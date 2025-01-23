defmodule Enkla do

  def double(n) do
    n*2
  end

  def f(n) do
    (n-32/1.8)
  end

  def rec(a, b) do
    a*b
  end

  def sqr(a) do
    a*a
  end

  def cir(n) do
    n*n*3.14
  end

 def product_case(m, n) do #8,8
    case m do
        0 -> 0
        1 -> n
        _-> product_case(m - 1, n) + n #7,8 + 8
    end
 end

 def exp(x, n) do #3,8
    case x do
        0 -> 1
        1 -> n
        2 -> product_case(n,n)
        _-> product_case(n, exp(x - 1, n))
    end
end

def exp_mul(x, n) do
    case n do
    0 -> 1
    1 -> x
    if rem(n,2) do  (x * exp_mul(n/2 - 1, x)) * x end
    _->  (x * exp_mul((n - 1) - 1, x)) * x
    end
end

def insert(elem, []) do
  [elem]
end

def insert(elem, sorted) do
[h | t] = sorted
if elem < h do [elem | [h | t]] else [h | insert(elem, t)] end
end

def isort(l) do
  isort(l, [])
  end

  def isort(l, sorted) do
  case l do
  [] -> sorted
  [h | t] -> isort(t, insert(h, sorted))
  end
end

def isortclause(l) do
  isortclause(l, [])
end

def isortclause([], sorted) do
  sorted
end

def isortclause([h | t], sorted) do
  isortclause(t, insert(h, sorted))
end

end
