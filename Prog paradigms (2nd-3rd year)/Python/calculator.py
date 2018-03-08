# Copyright (C) 2018  Ahmad A. A. (https://github.com/bbpgrs/)
# based on template provided in PLY Docs (http://www.dabeaz.com/ply/)

import ply.lex as lex
import ply.yacc as yacc

# === Lexical tokens component ===

# List of possible token namesthat can be produced by the lexer
# NAME: variable name, L/RPAREN: Left/Right Parenthesis
tokens = (
    'NAME', 'NUMBER',
    'PLUS', 'MINUS', 'TIMES', 'DIVIDE', 'MODULO', 'EQUALS',
    'LPAREN', 'RPAREN',
)

# Regular expression rules for tokens format: t_<TOKEN>
# Simple tokens: regex for literals +,-,*,/,%,=,(,) and variable names (alphanumeric)
t_PLUS    = r'\+'
t_MINUS   = r'-'
t_TIMES   = r'\*'
t_DIVIDE  = r'/'
t_MODULO  = r'%'
t_EQUALS  = r'='
t_LPAREN  = r'\('
t_RPAREN  = r'\)'
t_NAME    = r'[a-zA-Z_][a-zA-Z0-9_]*'


# complex tokens
# number token
def t_NUMBER(t):
    r'\d+'  # digit special character regex
    t.value = int(t.value)  # convert str -> int
    return t


# Ignored characters
t_ignore = " \t"  # spaces & tabs regex

# newline character
def t_newline(t):
    r'\n+'  # newline special character regex
    t.lexer.lineno += t.value.count("\n")  # increase current line number accordingly


# error handling for invalid character
def t_error(t):
    print("Illegal character '%s'" % t.value[0])  # print error message with causing character
    t.lexer.skip(1)  # skip invalid character


# Build the lexer
lex.lex()

# === Yacc parsing/grammar component ===

# Precedence & associative rules for the arithmetic operators
# 1. Unary, right-associative minus.
# 2. Binary, left-associative multiplication, division, and modulus
# 3. Binary, left-associative addition and subtraction
# Parenthesis precedence defined through the grammar
precedence = (
    ('left', 'PLUS', 'MINUS'),
    ('left', 'TIMES', 'DIVIDE', 'MODULO'),
    ('right', 'UMINUS'),
)

# dictionary of names (for storing variables)
names = {}

# --- Grammar:
# <statement> -> NAME = <expression> | <expression>
# <expression> -> <expression> + <expression>
#               | <expression> - <expression>
#               | <expression> * <expression>
#               | <expression> / <expression>
#               | <expression> % <expression>
#               | - <expression>
#               | ( <expression> )
#               | NUMBER
#               | NAME
# ---
# defined below using function definitions with format string/comment
# followed by logic of changing state of engine


# assignment statement: <statement> -> NAME = <expression>
def p_statement_assign(p):
    'statement : NAME EQUALS expression'
    names[p[1]] = p[3]  # PLY engine syntax, p stores parser engine state


# expression statement: <statement> -> <expression>
def p_statement_expr(p):
    'statement : expression'
    print(p[1])


# binary operator expression: <expression> -> <expression> + <expression>
#                                          | <expression> - <expression>
#                                          | <expression> * <expression>
#                                          | <expression> / <expression>
#                                          | <expression> % <expression>
def p_expression_binop(p):
    '''expression : expression PLUS expression
                          | expression MINUS expression
                          | expression TIMES expression
                          | expression DIVIDE expression
                          | expression MODULO expression'''
    if p[2] == '+':
        p[0] = p[1] + p[3]
    elif p[2] == '-':
        p[0] = p[1] - p[3]
    elif p[2] == '*':
        p[0] = p[1] * p[3]
    elif p[2] == '/':
        p[0] = p[1] / p[3]
    elif p[2] == '%':
        p[0] = p[1] % p[3]


# unary minus operator expression: <expression> -> - <expression>
def p_expression_uminus(p):
    'expression : MINUS expression %prec UMINUS'
    p[0] = -p[2]


# parenthesis group expression: <expression> -> ( <expression> )
def p_expression_group(p):
    'expression : LPAREN expression RPAREN'
    p[0] = p[2]


# number literal expression: <expression> -> NUMBER
def p_expression_number(p):
    'expression : NUMBER'
    p[0] = p[1]


# variable name literal expression: <expression> -> NAME
def p_expression_name(p):
    'expression : NAME'
    # attempt to lookup variable in current dictionary, throw error if not found
    try:
        p[0] = names[p[1]]
    except LookupError:
        print("Undefined name '%s'" % p[1])
        p[0] = 0


# handle parsing errors
def p_error(p):
    print("Syntax error at '%s'" % p.value)


# build parser
yacc.yacc()

# start interpreter and accept input using commandline/console
while True:
    try:
        s = input('calc > ')  # get user input. use raw_input() on Python 2
    except EOFError:
        break
    yacc.parse(s)  # parse user input string

