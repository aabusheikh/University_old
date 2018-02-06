#include "Card.h"

ostream& operator<<(ostream& out, const Card& card)
{
    card.print(out);
    return out;
}

void Card::print(ostream &out) const
{
    out << getType();
}

/* ---------------------------------------------------------------- */

string Quartz::getName() const
{
    return "Quartz";
}

int Quartz::getCoinsPerCards(int cards) const
{
    return (cards < 4) ? 0
           : (cards < 6) ? 1
           : (cards < 8) ? 2
           : (cards < 10) ? 3
           : 4;
}

char Quartz::getType() const
{
    return 'Q';
}

/* ---------------------------------------------------------------- */

string Hematite::getName() const
{
    return "Hematite";
}

int Hematite::getCoinsPerCards(int cards) const
{
    return (cards < 3) ? 0
           : (cards < 6) ? 1
           : (cards < 8) ? 2
           : (cards < 9) ? 3
           : 4;
}

char Hematite::getType() const
{
    return 'H';
}

/* ---------------------------------------------------------------- */

string Obsidian::getName() const
{
    return "Obsidian";
}

int Obsidian::getCoinsPerCards(int cards) const
{
    return (cards < 3) ? 0
           : (cards < 5) ? 1
           : (cards < 7) ? 2
           : (cards < 8) ? 3
           : 4;
}

char Obsidian::getType() const
{
    return 'O';
}

/* ---------------------------------------------------------------- */

string Malachite::getName() const
{
    return "Malachite";
}

int Malachite::getCoinsPerCards(int cards) const
{
    return (cards < 3) ? 0
           : (cards < 5) ? 1
           : (cards < 6) ? 2
           : (cards < 7) ? 3
           : 4;
}

char Malachite::getType() const
{
    return 'M';
}

/* ---------------------------------------------------------------- */

string Turquoise::getName() const
{
    return "Turquoise";
}

int Turquoise::getCoinsPerCards(int cards) const
{
    return (cards < 2) ? 0
           : (cards < 4) ? 1
           : (cards < 6) ? 2
           : (cards < 7) ? 3
           : 4;
}

char Turquoise::getType() const
{
    return 'T';
}

/* ---------------------------------------------------------------- */

string Ruby::getName() const
{
    return "Ruby";
}

int Ruby::getCoinsPerCards(int cards) const
{
    return (cards < 2) ? 0
           : (cards < 4) ? 1
           : (cards < 5) ? 2
           : (cards < 6) ? 3
           : 4;
}

char Ruby::getType() const
{
    return 'R';
}

/* ---------------------------------------------------------------- */

string Amethyst::getName() const
{
    return "Amethyst";
}

int Amethyst::getCoinsPerCards(int cards) const
{
    return (cards < 2) ? 0
           : (cards < 3) ? 1
           : (cards < 4) ? 2
           : (cards < 5) ? 3
           : 4;
}

char Amethyst::getType() const
{
    return 'A';
}

/* ---------------------------------------------------------------- */

string Emerald::getName() const
{
    return "Emerald";
}

int Emerald::getCoinsPerCards(int cards) const
{
    return (cards < 2) ? 0
           : (cards < 3) ? 2
           : 3;
}

char Emerald::getType() const
{
    return 'E';
}


