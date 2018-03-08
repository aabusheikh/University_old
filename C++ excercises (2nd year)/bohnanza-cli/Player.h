/*
 * Copyright (C) 2018  Ahmad A. A. (https://github.com/bbpgrs/), 
 *                     Nicholas Gagnon (https://github.com/ngagnon)
 */

#ifndef CSI2372_PLAYER_H
#define CSI2372_PLAYER_H

#include <string>

#include "Chain.h"
#include "ChainFactory.h"
#include "Hand.h"

using std::string;

class Player
{
public:
    /**
     * Constructor which accepts an istream and reconstruct the Player from file.
     * @param Input stream
     * @param Card factory
     */
    Player(istream&, CardFactory*);

    /**
     * Destructor which deletes the chains allocated to that player.
     */
    ~Player();

    /**
     * Serializes the Player to file. Intended to be reconstructed with the istream constructor.
     * @param Output stream
     */
    void serializeTo(ostream&) const;

    /**
     * Constructor that creates a Player with a given name.
     * @param Player's name
     */
    Player(string&);

    /**
     * Get the name of the player.
     * @return Player's name
     */
    string getName() const { return d_name; }

    /**
     * Get the number of coins currently held by the player.
     * @return Number of coins
     */
    int getNumCoins() const { return d_numCoins; }

    /**
     * Add a new card to the player's hand
     * @param New card
     * @return This
     */
    Player& operator+=(const Card*);

    /**
     * Add a number of coins
     * @param Number of coins to add
     * @return This
     */
    Player& operator+=(int);

    /**
     * Get the maximum number of chains this player can build (2 by default, 3 if he paid the right to have
     * a third chain)
     * @return Either 2 or 3.
     */
    int getMaxNumChains() const { return d_maxNumChains; }

    /**
     * Returns the number of non-zero chains (basically the number of chains being built)
     * @return Number of chains
     */
    int getNumChains() const { return d_numChains; }

    /**
     * Returns the chain at the given index.
     * @param Chain index (from 0)
     * @return Chain
     */
    Chain_Base& operator[] (int index) const;

    /**
     * Whether there is a chain at the given index
     * @param Chain index (from 0)
     * @return True if there is a chain at that index, false otherwise
     */
    bool chainExists (int index) const;

    /**
     * Adds an empty third chain to the player for two coins. The functions reduces the coin count
     * for the player by two. If the player does not have enough coins then an exception
     * NotEnoughCoins is thrown.
     */
    void buyThirdChain();

    /**
     * Whether the player has a chain that can accomodate the given card (i.e. a chain with the same
     * gemstone type as the given card).
     * @param Card
     * @return Chain index that has the adequate gemstone face, or -1 if no chain has same type
     */
    int hasChainFor(const Card*) const;

    /**
     * Start a new chain with the given card.
     * @param New card
     * @return Index of the newly created chain, or -1 if the player can't have any more chains
     */
    int startChain(const Card*);

    /**
     * Sells the chain at the given index. Give the player the amount of coins that this chain was worth.
     * @param Chain index (from 0)
     * @return Number of coins the player has gained (or -1 on error)
     */
    int sellChain(int);

    /**
     * Removes and returns the top card from the player's hand
     * @return Top card
     */
    const Card* playTopCard();

    /**
     * Prints the top card of the player's hand (with argument false) or all of the player's hand
     * (with argument true) to the supplied ostream.
     * @param Output stream
     * @param Whether to print the whole hand (true), or just the top card (false)
     */
    void printHand(std::ostream&, bool) const;

    /**
     * Get the number of cards currently in the player's hand
     * @return
     */
    int getNumCards() const;

    /**
     * Discards the card at the specified index from the player's hand
     * @param Card index (from 0)
     * @return Pointer to the card that was just discarded (to put in the discard pile)
     */
    const Card* discard(int);

    /**
     * Insertion operator to print a Player to an std::ostream. The player's name, the number of
     * coins in the player's possession and each of the chains (2 or 3, some possibly empty) will be printed.
     * @param Output stream
     * @param Player
     * @return
     */
    friend ostream& operator<<(ostream&, const Player&);

private:
    string d_name;
    int d_numCoins;
    int d_maxNumChains;
    int d_numChains;
    Hand d_hand;
    ChainFactory d_chainFactory;
    Chain_Base* d_chains[3] = {nullptr, nullptr, nullptr};
};


#endif //CSI2372_PLAYER_H

