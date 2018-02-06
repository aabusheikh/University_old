#ifndef CSI2372_HAND_H
#define CSI2372_HAND_H

#include <iostream>
#include <deque>
#include <queue>

#include "Card.h"
#include "CardFactory.h"

using std::istream;
using std::queue;
using std::deque;

class Hand : protected queue<const Card*, deque<const Card*>>
{
public:
    /**
     * Constructor which creates a new empty hand.
     */
    Hand() = default;

    /**
     * Constructor which accepts an istream and reconstruct the Hand from file.
     * @param Input stream
     * @param Card factory
     */
    Hand(istream&, CardFactory*);

    /**
     * Serializes the Player to file. Intended to be reconstructed with the istream constructor.
     * @param Output stream
     */
    void serializeTo(ostream&) const;

    /**
     * Adds the card to the rear of the hand.
     * @param New card
     * @return This
     */
    Hand& operator+=(const Card*);

    /**
     * Returns and removes the top card from the player's hand.
     * @return Removed card
     */
    const Card* play();

    /**
     * Returns but does not remove the top card from the player's hand.
     * @return Card
     */
    const Card* top() const;

    /**
     * Returns and removes the Card at a given index.
     * @param Card index (from 0)
     * @return Card
     */
    const Card* operator[](int);

    /**
     * Prints all of the player's hand to the supplied ostream.
     * @param Output stream
     */
    void print(ostream&) const;

    /**
     * Number of cards currently in the hand.
     * @return Number of cards.
     */
    int size() const;

    /**
     * Whether the player's hand is currently empty (useful near the end of the game)
     * @return True if the hand is empty, false otherwise
     */
    bool empty() const;

    /**
     * Insertion operator that prints all the cards in the player's hand to the given output stream
     * @param Output stream
     * @param Hand
     * @return
     */
    friend ostream& operator<<(ostream& os, const Hand& hand);
};


#endif //CSI2372_HAND_H
