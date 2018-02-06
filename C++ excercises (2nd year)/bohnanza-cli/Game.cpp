#include <iostream>
#include <fstream>
#include <sstream>

#include "Game.h"
#include "UI.h"
#include "CardFactory.h"

using std::istringstream;
using std::ofstream;
using std::cout;
using std::cin;

Game::Game(string player1Name, string player2Name) : d_table(player1Name, player2Name)
{
}

Game::Game(istream& stream, CardFactory* cardFactory) : d_table(stream, cardFactory)
{
}

bool Game::isFinished(string& winner)
{
    return d_table.win(winner);
}

void Game::playTurn()
{
    playTurnOf(d_table.d_player1);
    playTurnOf(d_table.d_player2);
}

bool Game::pause(string filePath)
{
    ofstream stream(filePath);

    if (!stream.good())
        return false;

    d_table.serializeTo(stream);

    return true;
}

void Game::playTurnOf(Player& player)
{
    cout << "\nPlaying the turn of " << player.getName() << endl;

    displayTable();
    letPlayerBuyThirdChain(player);

    consumeCardsInTradeArea(player, true);
    playTopmostCardOfHand(player);

    if (player.getNumCards() > 0 && UI::askYesNoQuestion("Would you like to play the next card from your hand as well? (y/n)"))
        playTopmostCardOfHand(player);

    if (player.getNumCards() > 0 && UI::askYesNoQuestion("Would you like to discard an arbitrary card from your hand? (y/n)"))
        discardArbitraryCardFromHand(player);

    fillUpTradeArea();
    consumeCardsInTradeArea(player, false);
    drawTwoCardsToBackOfHand(player);

    cout << "End of " << player.getName() << "'s turn" << endl;
    UI::pressEnterToContinue();
}

void Game::playTopmostCardOfHand(Player &player)
{
    if (player.getNumCards() == 0)
    {
        cout << "You cannot play a card from your hand as it is empty." << endl;
        return;
    }

    cout << "Playing topmost card from your hand: ";
    player.printHand(cout, false);
    cout << endl;

    const Card* topCard = player.playTopCard();
    int chainIndex = player.hasChainFor(topCard);

    if (chainIndex != -1)
    {
        cout << "The card will be added to your " << topCard->getName() << " chain" << endl;
        player[chainIndex] += topCard;
    }
    else if (player.getNumChains() < player.getMaxNumChains()
             && (player.getNumChains() == 0 || UI::askYesNoQuestion("Would you like to start a new chain with that card? (y/n)")))
    {
        int status = player.startChain(topCard);

        if (status == -1)
        {
            cout << "ERROR: Start chain failed!!" << endl;
            UI::pressEnterToContinue();
            return;
        }

        cout << "Created a new " << topCard->getName() << " chain" << endl;
        cout << "The card has been added to your " << topCard->getName() << " chain" << endl;
    }
    else
    {
        int numCoins = sellChain(player);

        cout << "Chain sold for " << numCoins << " coins" << endl;

        int status = player.startChain(topCard);

        if (status == -1)
        {
            cout << "ERROR: Start chain failed!!" << endl;
            UI::pressEnterToContinue();
            return;
        }

        cout << "Created a new " << topCard->getName() << " chain" << endl;
        cout << "The card has been added to your " << topCard->getName() << " chain" << endl;
    }

    cout << "Your new hand: ";
    player.printHand(cout, true);
    cout << endl;
}

void Game::consumeCardsInTradeArea(Player& player, bool discard)
{
    if (d_table.d_tradeArea.numCards() == 0)
        return;

    cout << "You now have to consume the trade area:" << endl;

    for (auto iter = d_table.d_tradeArea.begin(); iter != d_table.d_tradeArea.end();)
    {
        const Card* card = *iter;
        cout << "Card: " << card->getName() << endl;

        bool remove = false;
        int chainIndex = player.hasChainFor(card);

        if (chainIndex == -1) /* no chain with that gemstone face */
        {
            if (discard)
            {
                cout << "DISCARDED (none of your chains has that gemstone face)" << endl;
                d_table.d_discardPile += card;
                remove = true;
            }
            else
                cout << "LEFT IN (none of your chains has that gemstone face)" << endl;
        }
        else
        {
            string question = "Would you like to add that card to your " + card->getName() + " chain? (y/n) ";

            if (UI::askYesNoQuestion(question))
            {
                Chain_Base& chain = player[chainIndex];
                chain += card;
                remove = true;
            }
            else if (discard)
            {
                cout << "DISCARDED" << endl;
                d_table.d_discardPile += card;
                remove = true;
            }
            else
                cout << "LEFT IN" << endl;
        }

        if (remove)
            iter = d_table.d_tradeArea.take(iter);
        else
            iter++;
    }

    cout << endl;
}

void Game::letPlayerBuyThirdChain(Player &player)
{
    if (player.getNumCoins() >= 3 && player.getMaxNumChains() == 2
        && UI::askYesNoQuestion("Would you like to buy a third chain? (y/n) "))
    {
        player.buyThirdChain();
    }
}

void Game::displayTable()
{
    cout << endl << "-----------------------------------------------" << endl;
    cout << d_table;
    cout << "-----------------------------------------------" << endl << endl;
}

int Game::sellChain(Player& player)
{
    cout << "You must sell one of your chains: " << endl << endl;

    int maxChainIndex = -1;

    for (int i = 0; i < player.getMaxNumChains(); i++)
    {
        if (player.chainExists(i))
        {
            Chain_Base &chain = player[i];
            cout << (i + 1) << ". " << chain << " (worth " << chain.sell() << " coins)" << endl;

            if (i > maxChainIndex)
                maxChainIndex = i;
        }
    }

    cout << endl;

    int chosenOption = UI::chooseFromOptions(1, maxChainIndex + 1);
    return player.sellChain(chosenOption - 1);
}

void Game::discardArbitraryCardFromHand(Player& player)
{
    cout << "Which one would you like to discard?" << endl << endl;

    for (int i = 0; i < player.getNumCards(); i++)
        cout << (i + 1) << " ";

    cout << endl;
    player.printHand(cout, true);
    cout << endl;

    int chosenOption = UI::chooseFromOptions(1, player.getNumCards());
    const Card* card = player.discard(chosenOption - 1);
    d_table.d_discardPile += card;

    cout << "Discarded successfully." << endl;
    cout << "Your new hand: ";
    player.printHand(cout, true);
}

void Game::drawTwoCardsToBackOfHand(Player& player)
{
    for (int i = 0; i < 2; i++)
    {
        if (!d_table.d_deck.empty())
            player += d_table.d_deck.draw();
    }
}

void Game::fillUpTradeArea()
{
    cout << endl << "Drawing 3 cards from the deck to put in the trade area." << endl;

    for (int i = 0; i < 3 && !d_table.d_deck.empty(); i++)
        d_table.d_tradeArea += d_table.d_deck.draw();

    cout << "Drawing cards from the discard pile to the trade area until they don't match." << endl;

    while (!d_table.d_discardPile.empty() && d_table.d_tradeArea.legal(d_table.d_discardPile.top()))
    {
        d_table.d_tradeArea += d_table.d_discardPile.pickUp();
    }

    cout << d_table.d_tradeArea << endl;
}


