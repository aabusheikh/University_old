<?php
/**
 *  Estimating Pi using random points in PHP
 */

function calcPi() {
    $count = 0;
    $targetCount = 0;
    $outsideCount = 0;
    while ($count <= 1000000) {
        if (sqrt(pow((mt_rand(0, mt_getrandmax() - 1) / mt_getrandmax()), 2) + pow((mt_rand(0, mt_getrandmax() - 1) / mt_getrandmax()), 2)) > 1) {
            $outsideCount = $outsideCount + 1;

            $count = $count + 1;
        } else {
            $targetCount = $targetCount + 1;
            $count = $count + 1;
        }
    }
    echo (($targetCount * 4) / $count);
}

calcPi();

?>