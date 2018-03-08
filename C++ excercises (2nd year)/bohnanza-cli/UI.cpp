/*
 * Copyright (C) 2018  Ahmad A. A. (https://github.com/bbpgrs/), 
 *                     Nicholas Gagnon (https://github.com/ngagnon)
 */

#include "UI.h"

#include <iostream>
#include <sstream>

using std::cout;
using std::cin;
using std::endl;
using std::istringstream;

bool UI::askYesNoQuestion(string question)
{
    char answer = ' ';

    while (answer != 'y' && answer != 'n')
    {
        string input;

        cout << question << " ";
        getline(cin, input);

        answer = !input.empty() ? input.at(0) : ' ';
    }

    return (answer == 'y');
}

void UI::pressEnterToContinue()
{
    cout << "Press ENTER to continue..." << endl;
    cin.ignore();
}

int UI::chooseFromOptions(int min, int max)
{
    int chosenOption = -1;
    bool first = true;

    while (chosenOption < min || chosenOption > max)
    {
        if (first)
            first = false;
        else
        {
            cout << "INVALID OPTION" << endl;
            cout << "Please try again: ";
        }

        string input;
        getline(cin, input);

        chosenOption = -1;

        if (!input.empty())
            istringstream(input) >> chosenOption;
    }

    return chosenOption;
}
