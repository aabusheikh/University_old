#ifndef CSI2372_GAME_H
#define CSI2372_GAME_H

#include <iostream>
#include <string>

#include "Table.h"

using std::string;
using std::istream;

/**
 * Game is a high-level abstraction level for the various game mechanics which breaks up the main game
 * loop into its major components.
 */
class Game
{
public:
    /**
     * Starts a new game.
     * @param Name of the first player
     * @param Name of the second player
     */
    Game(string player1Name, string player2Name);

    /**
     * Restores a game from file.
     * @param Source file path
     * @param Card factory
     */
    Game(istream&, CardFactory*);

    /**
     * Tests wheter the game is finished. A game is finished when there aren't any cards left on the Deck.
     * @param Name of winner (set by reference) if game is finished
     * @return true if game is finished, false otherwise
     */
    bool isFinished(string& winner);

    /**
     * Plays a full turn of the game (one player, then the next)
     */
    void playTurn();

    /**
     * Pauses the game and serializes its state to file.
     * @param Destination file path
     * @return true on success, false otherwise
     */
    bool pause(string filePath);

private:
    Table d_table;

    void playTurnOf(Player&);
    void displayTable();
    void letPlayerBuyThirdChain(Player&);
    void consumeCardsInTradeArea(Player&, bool discard);
    void playTopmostCardOfHand(Player&);
    void discardArbitraryCardFromHand(Player&);
    int sellChain(Player&);
    void drawTwoCardsToBackOfHand(Player &player);
    void fillUpTradeArea();
};


#endif //CSI2372_GAME_H
