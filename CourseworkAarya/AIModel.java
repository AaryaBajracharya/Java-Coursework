package CourseworkAarya;



/**
 * AIModel class represents the base structure of an AI model.
 * It stores model details such as name, context window, price, 
 * and parameter count, and provides methods for checking token limits.
 * 
 * Author:Aarya Bajracharya
 * Version: Milestone 1
 * 
 */
public abstract class AIModel
{
    // Fields

    private String modelName;
    private int contextWindowSize;
    private float price;
    private int modelParameterCount;

    // Constructor
    public AIModel(String modelName, int contextWindowSize, float price, int modelParameterCount)
    {
        this.modelName = modelName;
        this.contextWindowSize = contextWindowSize;
        this.price = price;
        this.modelParameterCount = modelParameterCount;
    }

    // Methods 
    public boolean isWithinContext(String promptText, int outputTokens)
    {
        int systemTokens = 500;              // System tokens reserved for AI processing
        int inputTokens = promptText.length()/4;  // Approximation: 1 token ≈ 4 characters

        int total = systemTokens + inputTokens + outputTokens;

        return total <= contextWindowSize;
    }

    public String getModelName()
    { 
        return modelName;
    }

    public int getContextWindowSize()
    { 
        return contextWindowSize;
    }

    public float getPrice()
    {
        return price;
    }

    public int getModelParameterCount()
    { 
        return modelParameterCount;
    }

    public String display()
    {
        return "Model Name: " + modelName +
        "\nContext Window: " + contextWindowSize + " tokens" +
        "\nPrice (per 1 lakh tokens): NPR " + price +
        "\nParameter Count: " + modelParameterCount + " Billion";
    }
}
