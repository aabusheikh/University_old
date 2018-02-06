#include <iostream>
#include <fstream>

#include "Game.h"
#include "UI.h"

using std::cin;
using std::cout;
using std::endl;
using std::ifstream;

static Game setup();
static bool fileExists(string filePath);

int main()
{
    Game game = setup();
    string winner;

    while (!game.isFinished(winner))
    {
        if (UI::askYesNoQuestion("Would you like to pause the game ? (y/n)"))
        {
            string filePath;

            while (filePath.empty())
            {
                cout << "Enter the full path to the output file: ";
                getline(cin, filePath);

                if (fileExists(filePath)
                    && !UI::askYesNoQuestion("This file already exists. Do you wish to overwrite it? (y/n) "))
                {
                    filePath = "";
                }
            }

            game.pause(filePath);
            cout << "Your game was saved to file successfully." << endl;
            return 0;
        }

        game.playTurn();
    }

    cout << endl << endl << endl << "GAME OVER" << endl;
    cout << winner << " has won!" << endl;

    return 0;
}

static Game setup()
{
    cout << "MAIN MENU" << endl << endl;
    cout << "1. Start a new game" << endl;
    cout << "2. Continue a paused game" << endl << endl;

    int choice = UI::chooseFromOptions(1, 2);

    if (choice == 1)
    {
        string player1Name, player2Name;

        while (player1Name.empty())
        {
            cout << "Enter the name of player 1: ";
            getline(cin, player1Name);

            if (player1Name.length() > 20)
            {
                cout << "Name too long. Please keep it under 20 characters." << endl;
                player1Name = "";
            }
        }

        while (player2Name.empty())
        {
            cout << "Enter the name of player 2: ";
            getline(cin, player2Name);

            if (player2Name.length() > 20)
            {
                cout << "Name too long. Please keep it under 20 characters." << endl;
                player2Name = "";
            }
        }

        return Game(player1Name, player2Name);
    }
    else
    {
        string filePath;
        ifstream fileStream;

        while (filePath.empty() || !fileExists(filePath))
        {
            cout << "Enter the full path to the saved game file: ";
            getline(cin, filePath);

            if (!fileExists(filePath))
                cout << "ERROR: file not found." << endl;
        }

        fileStream = ifstream(filePath);

        return Game(fileStream, CardFactory::getFactory());
    }
}

static bool fileExists(string filePath)
{
    ifstream fh(filePath);
    return fh.good();
}
