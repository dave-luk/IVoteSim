package iVoteSim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimulationDriver
{
	public final int				MAX_OPTIONS;

	private IVoteService			iVoteService;
	private List<ChoiceQuestion>	simQuestions;
	private List<Student>			studentlist;

	private int						index	= 0;
	public boolean					depleted;

	public SimulationDriver(int maxQuestion, int maxOptions)
	{
		this.MAX_OPTIONS = maxOptions;
		iVoteService = new IVoteService();
		studentlist = generateStudent();
		simQuestions = generateQuestionSet(maxQuestion);
		depleted = false;
	}

	private ChoiceQuestion generateQuestion(int max)
	{
		int maxChoice = (int) Math.rint(Math.random() * (max - 2)) + 2;
		int correctChoice = (int) Math.rint(Math.random() * (maxChoice - 1)) + 1;
		// System.out.println("<DEBUG> : Options#: " + maxChoice + " Correct #:
		// " + correctChoice);

		List<Integer> l = new ArrayList<>();

		for (int i = 0; i < correctChoice;)
		{
			int answ = (int) Math.rint(Math.random() * (maxChoice - 1));
			if (!l.contains(answ))
			{
				l.add(answ);
				i++;
			}
		}

		Collections.sort(l);

		ChoiceQuestion question = new ChoiceQuestion("Sample Question # " + (int) Math.rint(Math.random() * 100),
		        maxChoice, true, l.toArray(new Integer[0]));
		return question;
	}

	private List<ChoiceQuestion> generateQuestionSet(int max)
	{
		List<ChoiceQuestion> list = new ArrayList<>();
		int maxCount = (int) Math.rint(Math.random() * (max - 1)) + 1;
		for (int i = 0; i < maxCount; i++)
		{
			list.add(generateQuestion(MAX_OPTIONS));
		}
		return list;
	}

	private List<Student> generateStudent()
	{
		List<Student> list = new ArrayList<>();
		int max = (int) Math.rint(Math.random() * 20) + 20;
		for (int i = 0; i < max; i++)
		{
			list.add(new Student(i));
		}
		return list;
	}

	public void poll()
	{
		if (depleted)  return; 

		iVoteService.setCurrentQuestion(simQuestions.get(index));

		for (Student s : studentlist)
		{

			// Responses include blanks.
			s.answer(simQuestions.get(index));
			iVoteService.addResponse(s, s.getResponse());

			if ((Math.random() * 100) > 80) // 20% of second guessing
			{
				s.answer(simQuestions.get(index));
				iVoteService.addResponse(s, s.getResponse());
			}
		}
	}

	public void next()
	{
		index++;
		if (index >= simQuestions.size())
		{
			depleted = true;
		}
	}

	public String printStat()
	{
		return "Questions: " + simQuestions.size() + "\n" + "Students: " + studentlist.size() + "\n ";
	}

	public String printPoll()
	{
		return iVoteService.displayStatistics();
	}

	public String printQuestion()
	{
		return simQuestions.get(index).toString() + "\n" + simQuestions.get(index).getAnswer().toString();
	}

	public static void main(String[] args)
	{
		SimulationDriver sd = new SimulationDriver(5, 10);
		System.out.println(sd.printStat());
		while (!sd.depleted)
		{
			sd.poll();
			System.out.println(sd.printPoll() + "\n" + sd.printQuestion() + "\n");
			sd.next();
		}
	}
}
