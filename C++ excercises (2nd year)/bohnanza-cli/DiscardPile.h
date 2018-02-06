#ifndef CSI2372_DISCARDPILE_H
#define CSI2372_DISCARDPILE_H

#include <iostream>
#include <vector>

#include "Card.h"
#include "CardFactory.h"

using std::istream;
using std::vector;

class DiscardPile
{
public:
    /**
     * Constructor which creates an empty discard pile.
     */
    DiscardPile();

    /**
     * Constructor which accepts an istream and reconstructs the DiscardPile from file.
     * @param Input stream
     * @param Card factory
     */
    DiscardPile(istream&, CardFactory*);

    /**
     * Serializes the DiscardPile to file. Intended to be reconstructed with the istream constructor.
     * @param Output stream
     */
    void serializeTo(ostream&) const;

    /**
     * Discards the given card to the pile.
     * @param Card to discard
     * @return this
     */
    DiscardPile& operator+=(const Card*);

    /**
     * Returns and removes the top card from the discard pile.
     * @return Top card being removed
     */
    const Card* pickUp();

    /**
     * Returns but does not remove the top card from the discard pile.
     * @return Top card
     */
    const Card* top() const;

    /**
     * Whether the discard pile is currently empty.
     * @return True if empty, false otherwise
     */
    bool empty() const;

    /**
     * Insertion operator that prints only the top card of the discard pile to an std::ostream.
     * @param Output stream
     * @param Discard pile
     * @return
     */
    friend ostream& operator<<(ostream& os, const DiscardPile& pile);

private:
    vector<const Card*> d_cards;
};


#endif //CSI2372_DISCARDPILE_H
