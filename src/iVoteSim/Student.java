package iVoteSim;

public class Student
{
	private int id;
	private QuestionResponse response;
	
	public Student(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return id;
	}

	public QuestionResponse getResponse()
	{
		return response;
	}

	public void answer(Question question)
	{
		response = question.createResponse();
		response.randomResponse();
	}
	
	
}
