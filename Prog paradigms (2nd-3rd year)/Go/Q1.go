/*
 * Copyright (C) 2018  Ahmad A. A. (https://github.com/bbpgrs/)
 */

package main

import (
	"fmt"
	"strings"
	"strconv"
)

func main() {
	homes := []Home{NewHouse(), NewSemi(), NewHouseRooms([]string{"bedroom1", "bedroom2"})}
	for i := 0; i<len(homes); i++ {
		homes[i].inputSqft()
		homes[i].printMetric()
	}
}

type House struct {
	rooms []string
	name string
	sizeFT []roomSz		
}

type roomSz struct {
	width float32
	length float32
}

type Home interface {
	inputSqft()
	printMetric()
}

func NewHouse() (house *House) {
	house.name = "House"
	house.rooms = []string{"kitchen", "living", "dining", "main"}
	house.sizeFT = make([]roomSz, 4)
	return
}

func NewHouseRooms(newRooms []string) (house *House) {
	house.name = "House"
	house.rooms = append([]string{"kitchen", "living", "dining", "main"}, newRooms...)
	house.sizeFT = make([]roomSz, len(house.rooms))
	return
}

func (house *House) inputSqft() {
	for i := 0; i < len(house.sizeFT); i++ {
		fmt.Printf("%s: width x length: ", house.rooms[i])
		var input string
		fmt.Scanf("%s", &input)
		n := strings.Index(input, "x")
		if ft, err := strconv.ParseFloat(input[:n], 32); err == nil {
			house.sizeFT[i].width = float32(ft)
		}
		if ft, err := strconv.ParseFloat(input[n+1:], 32); err == nil {
			house.sizeFT[i].length = float32(ft)
		}
	}
}
func (house *House) printMetric() {
	fmt.Printf("%s", house.name)
	for i := 0; i < len(house.sizeFT); i++ {
		wft := house.sizeFT[i].width
		lft := house.sizeFT[i].length
		wmt := wft/3.28084
		lmt := lft/3.28084
		
		fmt.Printf("%s x %t", wmt, lmt)
	}		
}

type Semi struct {
	House	
}

func NewSemi() (semi *Semi) {
	semi.name = "Semi"
	semi.rooms = []string{"kitchen", "living", "dining", "main"}
	semi.sizeFT = make([]roomSz, 4)
	return
}

func NewSemiRooms(newRooms []string) (semi *Semi) {
	semi.name = "House"
	semi.rooms = append([]string{"kitchen", "living", "dining", "main"}, newRooms...)
	semi.sizeFT = make([]roomSz, len(semi.rooms))
	return
}

func (semi *Semi) inputSqft() {
	for i := 0; i < len(semi.sizeFT); i++ {
		fmt.Printf("%s: width x length: ", semi.rooms[i])
		var input string
		fmt.Scanf("%s", &input)
		n := strings.Index(input, "x")
		if ft, err := strconv.ParseFloat(input[:n], 32); err == nil {
			semi.sizeFT[i].width = float32(ft)
		}
		if ft, err := strconv.ParseFloat(input[n+1:], 32); err == nil {
			semi.sizeFT[i].length = float32(ft)
		}
	}
}
func (semi *Semi) printMetric() {
	fmt.Printf("%s", semi.name)
	for i := 0; i < len(semi.sizeFT); i++ {
		wft := semi.sizeFT[i].width
		lft := semi.sizeFT[i].length
		wmt := wft/3.28084
		lmt := lft/3.28084
		
		fmt.Printf("%s x %t", wmt, lmt)
	}		
}
