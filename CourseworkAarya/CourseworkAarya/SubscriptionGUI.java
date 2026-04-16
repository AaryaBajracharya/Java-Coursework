package CourseworkAarya;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SubscriptionGUI extends JFrame {
    // ArrayList storing all AI model subscription plans.
    public ArrayList<AIModel> plans;

    // Initialise all text fields used in the GUI for input and display.
    public JTextField nameField,priceField,paramsField,contextField,promptsQuotaField,slotsField;

    public JTextField indexField,promptTextField,responseLengthField,teamMemberField,additionalPromptsField ;

    //  Display area 
    public JTextArea displayArea;

    // Plan filter
    public String currentFilter = "personal";

    // Left panel sections for visibility control
    public JPanel personalSection, proSection;

    //  Constructor:Constructs the SubscriptionGUI window and initialises all components.
    public SubscriptionGUI() {
        plans = new ArrayList<>();

        setTitle("AI Model Subscription System");
        setSize(1100, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main layout 
        JPanel MainPanel = new JPanel(new BorderLayout(10, 10));
        MainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        MainPanel.setBackground(new Color(250, 242, 230));
        MainPanel.add(HeaderPanel(), BorderLayout.NORTH);
        MainPanel.add(LeftPanel(),BorderLayout.WEST);
        MainPanel.add(CenterPanel(),BorderLayout.CENTER);
        MainPanel.add(RightPanel(),BorderLayout.EAST);

        add(MainPanel);
        updateLeftPanelVisibility();
    
    }

    //  Panel builders
   public JPanel HeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        headerPanel.setBackground(new Color(101, 67, 33));       
        headerPanel.setPreferredSize(new Dimension(0, 50));
        JLabel title = new JLabel("AI Model Subscription System");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        headerPanel.add(title);

       

        headerPanel.add(Box.createHorizontalStrut(20));
        JButton btn = makeButton("Display All", e -> displayAll());
        btn.setPreferredSize(new Dimension(140, 30));
        headerPanel.add(btn);
        JButton clearBtn = makeButton("Clear All", e -> clearAll());
        clearBtn.setPreferredSize(new Dimension(140, 30));
        headerPanel.add(clearBtn);
        JButton exportBtn = makeButton("Export to File", e -> { });
        exportBtn.setPreferredSize(new Dimension(140, 30));
        headerPanel.add(exportBtn);
        JButton loadBtn = makeButton("Load from File", e -> { });
        loadBtn.setPreferredSize(new Dimension(140, 30));
        headerPanel.add(loadBtn);

        return headerPanel;
    }

   public JPanel LeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(244, 231, 219));
        leftPanel.setPreferredSize(new Dimension(300, 0));

        JLabel AddPlanLabel = new JLabel("Add Plan");
        AddPlanLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        JButton personalBtn = makeButton("Personal Plans", e -> setFilter("personal"));
        JButton proBtn = makeButton("Pro Plans", e -> setFilter("pro"));
        
        leftPanel.add(AddPlanLabel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(personalBtn);
        leftPanel.add(proBtn);
        leftPanel.add(Box.createVerticalStrut(10));


        nameField    = makeField(leftPanel, "Model Name:");
        priceField   = makeField(leftPanel, "Price (NRs/Lakh tokens):");
        paramsField  = makeField(leftPanel, "Parameters (billions):");
        contextField = makeField(leftPanel, "Context Window:");

        // Personal Only
        personalSection = new JPanel();
        personalSection.setLayout(new BoxLayout(personalSection, BoxLayout.Y_AXIS));
        personalSection.setOpaque(false);
        promptsQuotaField = makeField(personalSection, "Initial Prompts Quota:");
        personalSection.add(makeButton("Add Personal Plan", e -> addPersonalPlan()));
        personalSection.add(Box.createVerticalStrut(50));

        additionalPromptsField = makeField(personalSection, "Additional Prompts:");
        personalSection.add(makeButton("Purchase Prompts", e -> purchasePrompts()));

        leftPanel.add(personalSection);



        // Pro Only
        proSection = new JPanel();
        proSection.setLayout(new BoxLayout(proSection, BoxLayout.Y_AXIS));
        proSection.setOpaque(false);
        slotsField = makeField(proSection, "Initial Team Slots:");
        proSection.add(makeButton("Add Pro Plan", e -> addProPlan()));
        proSection.add(Box.createVerticalStrut(50));
        
        teamMemberField = makeField(proSection, "Member Name:");
        JPanel teamBtns = new JPanel(new GridLayout(1, 2, 5, 0));
        teamBtns.setOpaque(false);
        teamBtns.setMaximumSize(new Dimension(300, 30));
        teamBtns.setAlignmentX(Component.LEFT_ALIGNMENT);
        teamBtns.add(makeButton("Add Member", e -> addTeamMember()));
        teamBtns.add(makeButton("Remove Member", e -> removeTeamMember()));
        proSection.add(teamBtns);

        leftPanel.add(proSection);


        return leftPanel;
    }

   public JScrollPane CenterPanel() {
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        displayArea.setMargin(new Insets(10, 10, 10, 10));
        displayArea.setBackground(new Color(251, 244, 233));
        JScrollPane scroll = new JScrollPane(displayArea);
        scroll.getViewport().setBackground(new Color(251, 244, 233));
        return scroll;
    }

    
   public JPanel RightPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(290, 0));
        panel.setBackground(new Color(244, 231, 219));

        // Index selector
        indexField = makeField(panel, "Index Number:");
        panel.add(makeButton("Check Plan Type", e -> checkPlanType()));
        panel.add(Box.createVerticalStrut(20));

        // Give a Prompt (Personal & Pro)
        
        promptTextField   = makeField(panel, "Prompt Text:");
        responseLengthField = makeField(panel, "Response Length (tokens):");
        panel.add(makeButton("Give a Prompt", e -> givePrompt()));

        return panel;
    }

    //  Helper Methods
   public JTextField makeField(JPanel parent, String labelText) {
        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(300, 30));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        parent.add(label);
        parent.add(field);
        parent.add(Box.createVerticalStrut(4));
        return field;
    }

   public JButton makeButton(String text, java.awt.event.ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(300, 30));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setBackground(new Color(197, 144, 104));
        btn.setForeground(new Color(59, 41, 28));
        btn.setFocusPainted(false);
        btn.addActionListener(listener);
        return btn;
    }

   
    //  Index validation
   public int getDisplayNumber() {
        int displayNumber = -1;
        try {
            int entered = Integer.parseInt(indexField.getText().trim());
            if (entered >= 0 && entered < plans.size()) {
                displayNumber = entered;
            } else {
                JOptionPane.showMessageDialog(this,
                    "Index out of range! Please enter a value between 0 and " + (plans.size() - 1) ,
                    "Index Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Invalid input! Please enter a whole number for the index.",
                "Input Error", JOptionPane.ERROR_MESSAGE);
        }
        return displayNumber;
    }

    
    //  Button handlers
    /**
     * Reads the shared fields and the slots field, then creates a new
     * PersonalPlan object and adds it to the plans ArrayList.
     * Validates input using try/catch for NumberFormatException.
     */

   public void addPersonalPlan() {
        try {
            String name    = nameField.getText().trim();
            double  price   =Double.parseDouble(priceField.getText().trim());
            int    params  = Integer.parseInt(paramsField.getText().trim());
            int    context = Integer.parseInt(contextField.getText().trim());
            int    prompts = Integer.parseInt(promptsQuotaField.getText().trim());

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Model name cannot be empty!");
                return;
            }

            plans.add(new PersonalPlan(name, context, price, params, prompts));
            JOptionPane.showMessageDialog(this, "Personal Plan added successfully at index " + (plans.size() - 1));
            clearAddFields();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Invalid input! Please ensure price, parameters, context window, and prompts quota are valid numbers.",
                "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Reads the shared fields and the slots field, then creates a new
     * ProPlan object and adds it to the plans ArrayList.
     * Validates input using try/catch for NumberFormatException.
     */
   public void addProPlan() {
        try {
            String name    = nameField.getText().trim();
            double  price   =Double.parseDouble(priceField.getText().trim());
            int    params  = Integer.parseInt(paramsField.getText().trim());
            int    context = Integer.parseInt(contextField.getText().trim());
            int    slots   = Integer.parseInt(slotsField.getText().trim());

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Model name cannot be empty!");
                return;
            }

            plans.add(new ProPlan(name, context, price, params, slots));
            JOptionPane.showMessageDialog(this, "Pro Plan added successfully at index " + (plans.size() - 1));
            clearAddFields();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Invalid input! Please ensure price, parameters, context window, and team slots are valid numbers.",
                "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Displays all plans in the displayArea text area, each prefixed with its
     * index number in the ArrayList.
     */
   public void displayAll() {
        if (plans.isEmpty()) {
            displayArea.setText("No plans added yet.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plans.size(); i++) {
            sb.append("Index: ").append(i).append("\n");
            sb.append(plans.get(i).display()).append("\n");
            sb.append("────────────────────").append("\n");
        }
        displayArea.setText(sb.toString());
    }

    /**
     * Sets the current filter and updates display and visibility.
     */
   public void setFilter(String filter)
   {
        currentFilter = filter;
        updateLeftPanelVisibility();
    }
     // Updates the visibility of left panel sections based on current filter.

   public void updateLeftPanelVisibility() {
        if ("personal".equals(currentFilter)) {
            personalSection.setVisible(true);
            proSection.setVisible(false);
        } else {
            personalSection.setVisible(false);
            proSection.setVisible(true);
        }
        revalidate();
        repaint();
    }

    /**
     * Clears all text fields and the display area, resetting the GUI to its
     * initial state.
     */
   public void clearAll() {
        clearAddFields();
        indexField.setText("");
        promptTextField.setText("");
        responseLengthField.setText("");
        teamMemberField.setText("");
        additionalPromptsField.setText("");
        displayArea.setText("");
    }

    /**
     * Reads the index, prompt text, and response length from the GUI.
     * Calls getDisplayNumber() to validate the index. If valid, uses instanceof
     * to check whether the plan is a PersonalPlan or ProPlan and calls the
     * appropriate givePrompt() method on the cast object.
     */
   public void givePrompt() {
        int index = getDisplayNumber();
        if (index == -1) return;

        String promptText = promptTextField.getText().trim();
        if (promptText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a prompt text.");
            return;
        }

        try {
            int responseLength = Integer.parseInt(responseLengthField.getText().trim());
            AIModel plan = plans.get(index);
            String result;

            if (plan instanceof PersonalPlan personalPlan) {
                result = personalPlan.givePrompt(promptText, responseLength);
            } else if (plan instanceof ProPlan proPlan) {
                result = proPlan.givePrompt(promptText, responseLength);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Unknown plan type at this index.",
                    "Plan Type Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, result);
            displayAll();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Invalid response length! Please enter a whole number.",
                "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Reads the index and additional prompts count from the GUI.
     * Calls getDisplayNumber() to validate the index. If valid and the plan
     * is a PersonalPlan, calls purchaseAdditionPrompts().
     */
   public void purchasePrompts() {
        int index = getDisplayNumber();
        if (index == -1) return;

        AIModel plan = plans.get(index);

        if (plan instanceof PersonalPlan personalPlan) {
            try {
                int extra = Integer.parseInt(additionalPromptsField.getText().trim());
                String result = personalPlan.purchaseAdditionalPrompts(extra);
                JOptionPane.showMessageDialog(this, result);
                additionalPromptsField.setText("");
                displayAll();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                    "Invalid number of prompts! Please enter a whole number.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Purchasing prompts is only available for Personal Plan subscriptions.",
                "Plan Type Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Reads the index and team member name from the GUI.
     * Calls getDisplayNumber() to validate the index. If valid and the plan
     * is a ProPlan, calls addTeamMembers().
     */
   public void addTeamMember() {
        int index = getDisplayNumber();
        if (index == -1) return;

        AIModel plan = plans.get(index);

        if (plan instanceof ProPlan proPlan) {
            String memberName = teamMemberField.getText().trim();
            if (memberName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a team member name.");
                return;
            }
            String result = proPlan.addTeamMembers(memberName);
            JOptionPane.showMessageDialog(this, result);
            teamMemberField.setText("");
            displayAll();
        } else {
            JOptionPane.showMessageDialog(this,
                "Team collaboration is only available for Pro Plan subscriptions.",
                "Plan Type Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Reads the index and team member name from the GUI.
     * Calls getDisplayNumber() to validate the index. If valid and the plan
     * is a ProPlan, calls removeTeamMembers().
     */
   public void removeTeamMember() {
        int index = getDisplayNumber();
        if (index == -1) return;

        AIModel plan = plans.get(index);

        if (plan instanceof ProPlan proPlan) {
            String memberName = teamMemberField.getText().trim();
            if (memberName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the team member name to remove.");
                return;
            }
            String result = proPlan.removeTeamMembers(memberName);
            JOptionPane.showMessageDialog(this, result);
            teamMemberField.setText("");
            displayAll();
        } else {
            JOptionPane.showMessageDialog(this,
                "Team collaboration is only available for Pro Plan subscriptions.",
                "Plan Type Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Uses the index from the GUI to determine and display the type of plan
     * at that index using the instanceof operator.
     * Displays "Personal Plan", "Pro Plan", or an unknown type message.
     */
   public void checkPlanType() {
        int index = getDisplayNumber();
        if (index == -1) return;

        AIModel plan = plans.get(index);
        String message;

        if (plan instanceof PersonalPlan) {
            message = "Plan at index " + index + " is a: Personal Plan";
        } else if (plan instanceof ProPlan) {
            message = "Plan at index " + index + " is a: Pro Plan";
        } else {
            message = "Plan at index " + index + " is of an unknown type.";
        }

        JOptionPane.showMessageDialog(this, message, "Plan Type", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Clears the text fields on the Add New Plan (left) panel.
     */
   public void clearAddFields() {
        nameField.setText("");
        priceField.setText("");
        paramsField.setText("");
        contextField.setText("");
        promptsQuotaField.setText("");
        slotsField.setText("");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SubscriptionGUI().setVisible(true));
    }
}