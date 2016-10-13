package iVoteSim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class IVoteService
{
	private ChoiceQuestion							currentQuestion;

	private Hashtable<Integer, QuestionResponse>	responses;

	private int correctCount, partialCorrectCount;
	
	public IVoteService()
	{
		responses = new Hashtable<>();
	}

	public Question getCurrentQuestion()
	{
		return currentQuestion;
	}

	public void setCurrentQuestion(ChoiceQuestion currentQuestion)
	{
		this.currentQuestion = currentQuestion;
		correctCount = 0;
		partialCorrectCount = 0;
	}

	public void addResponse(Student s, QuestionResponse r)
	{
		if (currentQuestion != null)
		{
			if (responses.containsKey(s.getId()))
			{
				responses.remove(s.getId());
			}
			responses.put(s.getId(), r);
		}
	}

	public String displayStatistics()
	{
		List<Integer> allAnswers = new ArrayList<>();
		List<QuestionResponse> rList = new ArrayList<>(responses.values());
		for (QuestionResponse r : rList)
		{
			if(currentQuestion.getAnswer().equals(r))
			{
				correctCount++;
			}
			else if(currentQuestion.getAnswer().contains(r))
			{
				partialCorrectCount++;
			}
			allAnswers.addAll(r.getResponse());
		}

		Collections.sort(allAnswers);
//		System.out.println("<DEBUG>: " + allAnswers);
		String str = "Stats:\n";
		str += "Answers recieved: " + responses.size() + "\n";
		for (int i = 0; i < currentQuestion.getChoices(); i++)
		{
			int count = allAnswers.lastIndexOf(i) - allAnswers.indexOf(i) + 1;
			str += Character.toString((char)( 65 + i)) + ": " + count + "\n";
		}
		str += "Correct Answers: " + correctCount + "\n";
		str += "Partial Answers: " + partialCorrectCount + "\n";
		return str;
	}
}
