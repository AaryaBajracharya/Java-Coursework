package CourseworkAarya;


/**
 * PersonalPlan class represents a subscription plan for individual users.
 * It extends AIModel and adds a monthly prompt quota.
 * 
 * Author: Aarya Bajracharya
 * Version: Milestone 1
 * 
 */
public class PersonalPlan extends AIModel
{
    //Fields
    private int promptsRemaining;

    //Constructor
    public PersonalPlan(String modelName, int contextWindowSize,double price, int modelParameterCount, int promptsRemaining)
    {
        super(modelName, contextWindowSize, price, modelParameterCount);
        this.promptsRemaining = promptsRemaining;
    }

    //Methods
  
    public int getPromptsRemaining()
    {
        return promptsRemaining;
    }

   
    public String purchaseAdditionalPrompts(int extraPrompts)
    {
        if(extraPrompts < 0)
        {
            return "Error: Please enter a positive value or upgrade to Pro Plan.";
        }

        promptsRemaining += extraPrompts;
        return "Successfully added " + extraPrompts + " prompts \nRemaining Prompts: " + promptsRemaining;
    }

    public String givePrompt(String promptText, int responseLength)
    {
        // Check context window limit
        if(!isWithinContext(promptText, responseLength))
        {
            return "Prompt Rejected: Context Limit Exceeded";
        }

        // Check remaining prompt quota  
        if(promptsRemaining > 0)
        {
            promptsRemaining--;
            return "Prompt submitted: " + promptText +
                   "\nExpected Response Length: " + responseLength + " tokens" +
                   "\nRemaining Prompts: " + promptsRemaining;
        }
        else
        {
            return "Monthly prompt limit reached. Please purchase more prompts or upgrade to Pro Plan";
        }
    }

   
    @Override
    public String display()
    {
        return super.display() + "\nPrompts Remaining: " + promptsRemaining;
    }
}
 
