defmodule SievePrimes do

  def sieve_one(n) do
    sieves_function(Enum.to_list(2..n))
  end

  def sieves_function([]), do: []

  def sieves_function([h|t]) do
    t = filter_out(h, t)
    [h | sieves_function(t)]
  end

  def filter_out(_, []), do: []

  def filter_out(e, [h|t]) do
    case rem h, e do
      0 -> filter_out(e, t)
      _ -> [h | filter_out(e, t)]
    end
  end

  def sieve_two(n) do
    sieves_function2(Enum.to_list(2..n), [])
  end

  def sieves_function2([], e), do: e

  def sieves_function2([h|t], e) do
    case divisible(h, e) do
      true -> sieves_function2(t, e)
      false -> sieves_function2(t, e ++ [h])
    end
  end

  def divisible(_, []), do: false

  def divisible(e, [h|t]) do
    case rem(e, h) do
      0 -> true
      _ -> divisible(e, t)
    end
  end

  def sieve_three(n) do
    sieves_function3(Enum.to_list(2..n), [])
    |> rev([])
  end

  def sieves_function3([], e), do: e

  def sieves_function3([h | t], e) do
    case divisible(h, e) do
      true -> sieves_function3(t, e)
      false -> sieves_function3(t, [h | e])
    end
  end

  def rev([], e), do: e
  def rev([h | t], e), do: rev(t, [h|e])

  def bench(n) do
      IO.inspect(:timer.tc(fn -> sieve_one(n) end))
      IO.inspect(:timer.tc(fn -> sieve_two(n) end))
      IO.inspect(:timer.tc(fn -> sieve_three(n) end))
      :ok
  end
end
