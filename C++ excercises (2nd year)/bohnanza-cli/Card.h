#ifndef CSI2372_CARD_H
#define CSI2372_CARD_H

#include <string>
#include <iostream>

using std::string;
using std::ostream;

class Card
{
public:
    /**
     * Returns how much the given number of cards is worth (in coins)
     * @param Number of cards
     * @return Number of coins which that many card can be sold for
     */
    virtual int getCoinsPerCards(int) const = 0;

    /**
     * Returns the full name of the card (e.g., Ruby).
     * @return Name of the card
     */
    virtual string getName() const = 0;

    /**
     * Returns the first character for the card, typically for use by print().
     * @return First character of card (e.g. R for Ruby card)
     */
    virtual char getType() const = 0;

    /**
     * Inserts the first character for the card into the output stream supplied as argument.
     * @param Output stream
     */
    void print(ostream& out) const;

    /**
     * Virtual destructor is required for calling new/delete on derived classes
     */
    virtual ~Card() = default;

    /**
     * Insertion operator which inserts the first character of the card into the supplied output stream.
     * @param Output stream
     * @param Card
     * @return Stream
     */
    friend ostream& operator<<(ostream&, const Card&);
};

class Quartz : public Card
{
public:
    int getCoinsPerCards(int cards) const override;
    string getName() const override;
    char getType() const override;
};

class Hematite : public Card
{
public:
    int getCoinsPerCards(int cards) const override;
    string getName() const override;
    char getType() const override;
};

class Obsidian : public Card
{
public:
    int getCoinsPerCards(int cards) const override;
    string getName() const override;
    char getType() const override;
};

class Malachite : public Card
{
public:
    int getCoinsPerCards(int cards) const override;
    string getName() const override;
    char getType() const override;
};

class Turquoise : public Card
{
public:
    int getCoinsPerCards(int cards) const override;
    string getName() const override;
    char getType() const override;
};

class Ruby : public Card
{
public:
    int getCoinsPerCards(int cards) const override;
    string getName() const override;
    char getType() const override;
};

class Amethyst : public Card
{
public:
    int getCoinsPerCards(int cards) const override;
    string getName() const override;
    char getType() const override;
};

class Emerald : public Card
{
public:
    int getCoinsPerCards(int cards) const override;
    string getName() const override;
    char getType() const override;
};

#endif //CSI2372_CARD_H
