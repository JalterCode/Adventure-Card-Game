Feature: Assignment 2 Tests


  Scenario: A1_scenario
    Given a new game is started
    And a randomized hand is dealt
    And the event deck is setup to draw the Q4 card first
    And adventure deck is set up to ensure players draw the correct cards
    And the players are dealt their correct initial hands
    When "P1" draws a quest of 4 stages and "declines"
    And "P2" accepts the sponsor
    And P2 builds their stages
    And P1, P3, P4 participate, discard cards, build and resolve attacks for stage 1
    And P1, P3, P4 participate, discard cards, build and resolve attacks for stage 2
    And P3, P4 participate, discard cards, build and resolve attacks for stage 3
    And P3, P4 participate, discard cards, build and resolve attacks for stage 4
    And "P2" trims their hand down to 12 cards from 16
    And Quest logic is resolved
    Then "P4" wins the quest, with 4 shields awarded
    And P1 should have no shields and the correct hand
    And P2 should have 12 cards in hand
    And P3 should have no shields and the correct hand
    And P4 should have 4 shields and the correct hand


    #ADD MORE COMMENTS TO SCENARIO 2
  Scenario: 2winner_game_2winner_quest
    Given a new game is started
    And the event deck is setup to draw Q4 then Q3
    And the players are dealt their correct initial hands
    And adventure deck is set up to ensure players draw the correct cards
    When "P1" draws a quest of 4 stages and "accepts"
    And P1 builds the quest with the first stage having only a foe
    And P1, P3, P4 participate, discard cards, build and resolve attacks for stage 1, P3 attack fails
    And P2 and P4 participate in and win stages 2, 3, and 4
    And "P1" trims their hand down to 12 cards from 16
    And Quest logic is resolved
    And "P2" draws a quest of 3 stages and "declines"
    And P3 sponsors the quest and builds the stages
    #MAYBE REMOVE THIS / REWORK LATER, MIGHT BE REDUNDANT IDK (P1 DECLINES TO PARTICIPATE)
    And P1 declines to participate
    And P2 and P4 participate in and win stages 1, 2, and 3
    And "P3" trims their hand down to 12 cards from 14
    And Quest logic is resolved
    Then P2 and P4 should each have 7 shields
    And P2 and P4 should be declared winners
    