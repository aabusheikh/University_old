#ifndef CSI2372_CHAINFACTORY_H
#define CSI2372_CHAINFACTORY_H

#include "Chain.h"

/**
 * Refactored chain instantiation routines from the Player class into a separate ChainFactory class
 * in order to more closely observe the single-responsibility principle. This class thus simply
 * exists to create Chain's of cards given a type.
 */
class ChainFactory
{
public:
    /**
     * Creates a chain for the given card, and inserts that card into the chain. For instance, when calling
     * this method with a Ruby card, it will create a Chain<Ruby> and add the Ruby card to the chain.
     * @param Card
     * @return Newly created chain
     */
    Chain_Base* getChain(const Card*) const;

    /**
     * Creates a chain from the supplied input stream. Intended for reconstruction from file.
     * @param Input stream
     * @param Card factory
     * @return Newly reconstructed chain
     */
    Chain_Base* getChain(istream&, CardFactory*) const;
};

#endif //CSI2372_CHAINFACTORY_H
