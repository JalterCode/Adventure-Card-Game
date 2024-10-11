public abstract class Card {
    protected String cardType;
    protected int amount;

    protected String name;

    public Card(String cardType, String name, int amount){
        this.cardType = cardType;
        this.amount = amount;
        this.name = name;
    }
    public String getCardType() {
        return cardType;
    }

    public int getAmount(){
        return amount;
    }

    public String getName(){
        return name;
    }




}
