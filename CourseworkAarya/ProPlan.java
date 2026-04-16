package CourseworkAarya;


/**
 * ProPlan class represents a subscription plan for professional users or teams.
 * It extends AIModel and provides unlimited prompts, team member management,
 * and context window validation.
 * 
 * Author: Aarya Bajracharya
 * Version: Milestone 1
 */
public class ProPlan extends AIModel
{
    //Fields 

    private int teamSlots;

    //Constructor

    public ProPlan(String modelName, int contextWindowSize, float price, int modelParameterCount, int teamSlots)
    {
        super(modelName, contextWindowSize, price, modelParameterCount);
        this.teamSlots = teamSlots;
    }

    //Methods 

    public String givePrompt(String promptText, int responseLength)
    {
        if(!isWithinContext(promptText, responseLength))
        {
            return "Prompt Rejected: Context Limit Exceeded";
        }

        return "Prompt Accepted for Pro Plan\nExpected Output Tokens: " + responseLength;
    }

    public String addTeamMembers(String memberName)
    {
        if(teamSlots > 0)
        {
            teamSlots--;
            return "Team Member " + memberName + " has been added\nRemaining Slots: " + teamSlots;
        }
        else
        {
            return "Error: No slots remain.";
        }
    }

    public String removeTeamMembers(String memberName)
    {
        if(teamSlots < 3)  // Maximum team size = 3 
        {
            teamSlots++;
            return "Team Member " + memberName + " has been removed\nRemaining Slots: " + teamSlots;
        }
        else
        {
            return "Error: No team members to remove.";
        }
    }
    public int getTeamSlots() {
    return teamSlots;
    }
    


    @Override
    public String display()
    {
        return super.display() + "\nSlots Available: " + teamSlots;
    }
}
