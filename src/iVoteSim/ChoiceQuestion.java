package iVoteSim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ChoiceQuestion extends Question
{
	public final int		questionChoices;
	public final boolean	allowMultiple;

	public ChoiceQuestion(String text, int choiceCount, boolean m, Integer... correct)
	{
		super(text);
		this.questionChoices = choiceCount;
		this.allowMultiple = m;
		Response ans = new Response();
		ans.setResponse(Arrays.asList(correct));
		this.answer = ans;
	}

	@Override
	public String toString()
	{
		String str = super.toString() + "\n";
		for (int i = 0; i < questionChoices; i++)
		{
			str += Character.toString((char) (65 + i)) + " ";
		}
		return str;
	}

	@Override
	public QuestionResponse createResponse()
	{
		return new Response();
	}

	public int getChoices()
	{
		return questionChoices;
	}

	@Override
	QuestionResponse getAnswer()
	{
		return answer;
	}

	public class Response extends QuestionResponse
	{
		private List<Integer> choices = new ArrayList<>();

		public List<Integer> getResponse()
		{
			return choices;
		}

		public void setResponse(Object choices)
		{
			if (choices instanceof List)
			{
				@SuppressWarnings("unchecked") List<Integer> c = (List<Integer>) choices;
				if (allowMultiple)
				{
					for (Integer i : c)
					{
						if (i > questionChoices) return;
					}
					this.choices = c;
				}
				else
				{
					if (c.size() == 1) this.choices = c;
				}
			}
		}

		@Override
		public boolean equals(Object o)
		{
			if (o instanceof Response)
			{
				for (Integer i : ((Response) o).getResponse())
				{
					if (!choices.contains(i)) return false;
				}
				return true;
			}
			return false;
		}

		@Override
		public void randomResponse()
		{
			List<Integer> answerList = new ArrayList<>();
			if (allowMultiple)
			{
				for (int i = 0; i < questionChoices; i++)
				{
					if ((Math.random() * 100) > 50)
					{
						answerList.add(i);
					}
				}

			}
			else
			{
				int r = new Random().nextInt(questionChoices);
				answerList.add((r + 1));
			}
			setResponse(answerList);
		}

		@Override
		public String toString()
		{
			String str = "Answer: ";
			for (Integer i : choices)
				str += Character.toString((char) (65 + i)) + " ";
			return str;
		}

		@Override
		public boolean contains(QuestionResponse r)
		{
			for (Integer i : choices)
			{
				if (r.getResponse().contains(i)) return true;
			}
			return false;
		}
	}
}
