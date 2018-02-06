<?php

/**
 *  Queens placement problem in PHP
 */

class Queens {
    /**
     * Return true if queen placement q[n] does not conflict with
     * other queens q[0] through q[n-1]
     *
     * @param array $q array of queens placement represented by integers
     * @param integer $n index in q to check consistency for
     * @return boolean true if consistent, false otherwise
     */
    function isConsistent($q, $n) {
        for ($i = 0; $i < $n; $i++) {
            if ($q[$i] == $q[$n])               return false;   // same column
            if (($q[$i] - $q[$n]) == ($n - $i)) return false;   // same diagonal 1
            if (($q[$n] - $q[$i]) == ($n - $i)) return false;   // same diagonal 2
        }
        return true;
    }

    /**
     * Prints n-by-n placement of queens from permutation q in ASCII.
     *
     * @param array $q array of queens placement represented by integers
     */
    function printQueens($q) {
        $n = count($q);
        for ($i = 0; $i < $n; $i++) {
            for ($j = 0; $j < $n; $j++) {
                if ($q[$i] == $j) echo "Q ";  // Queen placed here
                else              echo "_ ";  // No queen placed here
            }
            echo "<br>";
        }
        echo "<br>";
    }

    /**
     * Try all permutations using backtracking
     *
     * @param integer $n size of board/array, e.g. for a regular chess board n=8
     */
    function enumerateStart($n) {
        $a = array();
        for ($i=0; $i < $n; $i++) {
            $a[$i] = 0;
        }
        $this->enumerate($a, 0);
    }

    /**
     * Try all permutations using backtracking
     *
     * @param array $q initially empty array, then integer representation of queens placement
     * @param integer $k initially 0, incremented by 1 each call. passed to isConsistent as n.
     */
    function enumerate($q, $k) {
        $n = count($q);
        if ($k == $n) $this->printQueens($q);
        else {
            for ($i = 0; $i < $n; $i++) {
                $q[$k] = $i;
                if ($this->isConsistent($q, $k)) $this->enumerate($q, $k+1);
            }
        }
    }
}

// Create new Queens object and start enumeration process
$queens = new Queens();
$queens->enumerateStart(8);

?>
