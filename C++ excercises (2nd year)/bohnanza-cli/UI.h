#ifndef CSI2372_UI_H
#define CSI2372_UI_H

#include <string>

using std::string;

/**
 * User interface helper routines. Offers common UI functions such as asking a yes/no question, pausing
 * the game until the user hits ENTER, as well as chosing one of many menu options.
 */
class UI
{
public:
    /**
     * Ask a yes-no question to the user on the command line. Repeats the question until a valid answer
     * is provided.
     * @param Question to ask
     * @return True if the user answered yes, false otherwise
     */
    static bool askYesNoQuestion(string question);

    /**
     * Asks the user to choose from the given options. Repeatedly asks until a valid answer is provided.
     * @param Minimum acceptable value (inclusive)
     * @param Maximum acceptable value (inclusive)
     * @return Chosen option (>= min, <= max)
     */
    static int chooseFromOptions(int min, int max);

    /**
     * Waits until the user presses the ENTER key to resume the program.
     */
    static void pressEnterToContinue();
};

#endif //CSI2372_UI_H
