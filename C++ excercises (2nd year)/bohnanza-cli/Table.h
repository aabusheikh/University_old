/*
 * Copyright (C) 2018  Ahmad A. A. (https://github.com/bbpgrs/), 
 *                     Nicholas Gagnon (https://github.com/ngagnon)
 */

#ifndef CSI2372_TABLE_H
#define CSI2372_TABLE_H

#include <iostream>
#include <string>

#include "CardFactory.h"
#include "Player.h"
#include "Deck.h"
#include "TradeArea.h"
#include "DiscardPile.h"

using std::string;
using std::istream;
using std::ostream;

class Table
{
    friend class Game;

public:
    /**
     * Creates a table for a new game.
     * @param Name of player 1
     * @param Name of palyer 2
     */
    Table(string player1Name, string player2Name);

    /**
     * Constructor which accepts an istream and reconstruct the Table from file.
     * @param Input stream
     * @param Card factory
     */
    Table(istream&, CardFactory*);

    /**
     * Returns true when a player has won. The name of the player is returned by reference (in the argument).
     * The winning condition is that all cards from the deck must have been picked up and then the player
     * with the most coins wins.
     * @param Name of winning player (set by reference)
     * @return True if a player has won, false otherwise
     */
    bool win(string&) const;

    /**
     * Serializes the content of the Table to the given output stream. Intended for serialization to file.
     *
     * This is in fact the print() method given in the instruction, simply with a more accurate and descriptive name
     * @param Output stream
     */
    void serializeTo(ostream&) const;

    /**
     * Insertion operator to print a Table to an std::ostream. The two players, the discard pile, and the trading area
     * will be printed. This is the top level print out used to show the current state of the table to the players.
     * @param Output stream
     * @param Player
     * @return
     */
    friend ostream& operator<<(ostream& os, const Table& table);

private:
    /* Note: the order in which these declarations appear is crucial for serialization/deserialization to work */
    Player d_player1, d_player2;
    Deck d_deck;
    DiscardPile d_discardPile;
    TradeArea d_tradeArea;

    int finalCoinCount(const Player&) const;
};




#endif //CSI2372_TABLE_H
