#include "Hand.h"

Hand::Hand(istream& stream, CardFactory* factory)
{
    int numCards;

    stream >> numCards;

    for (int i = 0; i < numCards; i++)
        c.push_back(factory->getCard(stream));
}

void Hand::serializeTo(ostream& stream) const
{
    stream << size() << " "; /* pack the number of cards */

    /* followed by the code of each card */
    for (auto card  : c)
    {
        card->print(stream);
        stream << " ";
    }
}

Hand& Hand::operator+=(const Card* card)
{
    push(card);
    return *this;
}

const Card* Hand::play()
{
    const Card* card = top();

    if (card != nullptr)
        pop();

    return card;
}

const Card* Hand::top() const
{
    return !empty() ? front() : nullptr;
}

const Card* Hand::operator[](int index)
{
    if (index < 0 || index >= size())
        return nullptr; /* index out of bounds */

    const Card* card = c.at(index);
    c.erase(c.begin() + index);
    return card;
}

void Hand::print(ostream& os) const
{
    for (auto card : c)
    {
        card->print(os);
        os << ' ';
    }
}

int Hand::size() const
{
    return queue::size();
}


ostream& operator<<(ostream& os, const Hand& hand)
{
    hand.print(os);
    return os;
}

bool Hand::empty() const
{
    return queue::empty();
}


