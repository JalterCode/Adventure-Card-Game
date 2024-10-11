public class QuestCard extends Card {
    private int stages;
    private String name;




    public QuestCard(String cardType, String name, int stages, int amount) {
        super(cardType, name, amount);
        this.name = name;
        this.stages = stages;
    }

    public int getStages() {
        return stages;
    }


}
