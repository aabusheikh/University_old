#ifndef CSI2372_DECK_H
#define CSI2372_DECK_H

#include <iostream>
#include <vector>
#include <array>

#include "Card.h"

using std::istream;
using std::vector;
using std::array;

class CardFactory;

class Deck : protected vector<const Card*>
{
public:
    /**
     * Constructor which accepts an istream and reconstructs the deck from file.
     * @param Input stream
     * @param Card factory
     */
    Deck(istream&, CardFactory*);

    /**
     * Constructs a deck using the supplied card instances, after shuffling them around a little.
     * @param 104-card deck (not shuffled)
     */
    Deck(array<const Card*, 104>&);

    /**
     * Serializes the Deck to file. Intended to be reconstructed with the istream constructor.
     * @param Output stream
     */
    void serializeTo(ostream&) const;

    /**
     * Returns and removes the top card from the deck.
     * @return Card
     */
    const Card* draw();

    /**
     * Whether the deck is empty.
     * @return True if empty, false otherwise
     */
    bool empty() const;
};


#endif //CSI2372_DECK_H
