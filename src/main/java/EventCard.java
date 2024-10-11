public class EventCard extends Card {

    public EventCard(String cardType, String name, int amount) {
        super(cardType, name, amount);
    }

    public void handleEvent(EventCard card, Player player, Game game){
        int currentShields = player.getShields();
        if(card.getName().equals("Plague")){

            if(currentShields <= 2){
                player.setShields(0);
            } else{
                player.setShields(currentShields - 2);
            }
        }

    }
}
