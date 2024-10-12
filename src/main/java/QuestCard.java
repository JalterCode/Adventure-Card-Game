public class QuestCard extends Card {
    private int stages;
    private String name;

    private boolean sponsored;




    public QuestCard(String cardType, String name, int stages, int amount) {
        super(cardType, name, amount);
        this.name = name;
        this.stages = stages;
    }

    public int getStages() {
        return stages;
    }

    public boolean isSponsored(){
        return sponsored;
    }

    public void setSponsored(boolean sponsored){
        this.sponsored = sponsored;
    }


}
