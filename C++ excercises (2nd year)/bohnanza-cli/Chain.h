#ifndef CSI2372_CHAIN_H
#define CSI2372_CHAIN_H

#include <vector>
#include <ostream>
#include <iomanip>

#include "Card.h"
#include "CardFactory.h"
#include "IllegalType.h"

using std::vector;
using std::istream;
using std::endl;
using std::setw;
using std::left;

/**
 * We need this abstract chain interface to be able to reference chains of any type from the Player
 * class. It would not make any sense to keep a pointer to each type of chain (i.e.e Chain<Ruby>, Chain<Emerald>, etc.)
 */
class Chain_Base
{
public:
    /**
     * Virtual destructor is required to be able to call new/delete of derived templated classes.
     */
    virtual ~Chain_Base() = default;

    /**
     * Serializes the Chain to file. Intended to be reconstructed with the istream constructor.
     * @param Output stream
     */
    virtual void serializeTo(ostream&) const = 0;

    /**
     * Adds a card to the Chain. If the run-time type does not match the template type of
     * the chain an exception of type IllegalType will be raised.
     * @param New card
     * @return This
     */
    virtual Chain_Base& operator+=(const Card*) = 0;

    /**
     * Whether it is legal to add the given card to this chain (i.e. gemstone types match).
     * Although we could try the += operator and catch the IllegalType exception, it is much more
     * eleguant to simply have a method that's made specifically to test whether a card can be inserted.
     * @param New card
     * @return True if the gemstone face matches that of the chain, false otherwise
     */
    virtual bool legal(const Card*) const = 0;

    /**
     * Counts the number cards in the current chain and returns the number coins according
     * to the function Card::getCardsPerCoin().
     * @return Total number of coins the chain can be sold for
     */
    virtual int sell() const = 0;

    /**
     * Gets the number of cards stored in this chain.
     * @return Number of cards
     */
    virtual int getNumCards() const = 0;

    /**
     * Returns the complete name of the card type stored in this chain (e.g. Ruby for chain of Ruby)
     * @return Card type full name
     */
    virtual string getCardName() const = 0;

    /**
     * Prints the one-letter code of the chain's card type to the supplied output stream.
     * @param Output stream
     */
    virtual void printCard(ostream&) const = 0;

    /**
     * Insertion operator to print Chain on an std::ostream. It prints a start column with the full name
     * of the gemstone, followed by a one-letter code of the card face for each card in the chain, for instance
     * with a 5-card Ruby chain: Ruby R R R
     * @param Output stream
     * @param Chain
     * @return Stream
     */
    friend ostream& operator<<(ostream& os, const Chain_Base& chain);

    /**
	 * Actually prints the human-friendly textual representation of this chain. Called by the insertion
     * operator, and implemented in concrete Chain implementations according to the virtual friend
     * function idiom.
     * @param Output stream
	 */
    virtual void print(ostream& out) const = 0;
};

inline ostream& operator<<(ostream& os, const Chain_Base& chain)
{
    chain.print(os);
    return os;
}

template <class T>
class Chain : public Chain_Base
{
public:
    /**
     * Constructor which creates a new chain and inserts the given card into it.
     * @param First card of the chain
     */
    Chain(const Card* card)
    {
        d_cards.push_back(dynamic_cast<const T*>(card));
    }

    /**
     * Constructor which accepts an istream and reconstructs the chain from file when a
     * game is resumed.
     * @param Input stream
     * @param Card factory
     */
    Chain(istream& stream, CardFactory* factory)
    {
        int numCards;
        stream >> numCards;

        for (int i = 0; i < numCards; i++)
            d_cards.push_back(dynamic_cast<const T*>(factory->getCard(stream)));
    }

    int getNumCards() const override
    {
        return d_cards.size();
    }

    void serializeTo(ostream& stream) const override
    {
        stream << d_cards.size();

        for (int i = 0; i < d_cards.size(); i++)
            printCard(stream);
    }

    Chain_Base& operator+=(const Card* newCard) override
    {
        const T* castedCard = dynamic_cast<const T*>(newCard);

        if (castedCard != nullptr) /* dynamic_cast returns nullptr if downcast fails */
        {
            d_cards.push_back(castedCard);
            return *this;
        }
        else throw IllegalType();
    }

    bool legal(const Card* newCard) const override
    {
        return dynamic_cast<const T*>(newCard) != nullptr;
    }

    int sell() const override
    {
        auto numCards = d_cards.size();
        return getCoinsPerCards(numCards);
    }

    void printCard(ostream& out) const override
    {
        T card;
        card.print(out);
    }

    string getCardName() const override
    {
        T card;
        return card.getName();
    }

    void print(ostream& os) const override
    {
        os << left << setw(20) << getCardName() << ' ';

        for (int i = 0; i < getNumCards(); i++)
        {
            printCard(os);
            os << ' ';
        }

        os << endl;
    }


private:
    vector<const T*> d_cards;

    int getCoinsPerCards(int numCards) const
    {
        T card;
        return card.getCoinsPerCards(numCards);
    }
};


#endif //CSI2372_CHAIN_H
