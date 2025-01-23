defmodule Shunt do

  def find(_, []) do  end

  def find(xs, [h | t]) do
    {hs, ts} = split(xs, h)
    {main, one, two} = Moves.single({:one,Enum.count(ts) + 1},{xs,[],[]})
    {main, one, two} = Moves.single({:two, Enum.count(hs)},  {main, one, two})
    {main, one, two} = Moves.single({:one, a = -1 * Enum.count(one)},  {main, one, two})
    {main, _, _} = Moves.single({:two, b = -1 * Enum.count(two)},  {main, one, two})
    [_ | mt] = main
    [_ | wt] = [h | t]
    IO.inspect [{:one, Enum.count(ts) + 1}, {:two, Enum.count(hs)}, {:one, a},  {:two, b}]
    find(mt, wt)
  end

  def few(_, []) do  end

  def few([h|mt], [h|wt]) do few(mt, wt) end

  def few(xs, [h | t]) do
    {hs, ts} = split(xs, h)
    {main, one, two} = Moves.single({:one,Enum.count(ts) + 1},{xs,[],[]})
    {main, one, two} = Moves.single({:two, Enum.count(hs)},  {main, one, two})
    {main, one, two} = Moves.single({:one, a = -1 * Enum.count(one)},  {main, one, two})
    {main, _, _} = Moves.single({:two, b = -1 * Enum.count(two)},  {main, one, two})
    [_ | mt] = main
    [_ | wt] = [h | t]
    IO.inspect [{:one, Enum.count(ts) + 1}, {:two, Enum.count(hs)}, {:one, a},  {:two, b}]
    few(mt, wt)
  end

  def split([h | t], y)do
    num = Lists.position([h | t], y)
    num2 = Enum.count([h | t]) - (Enum.count([h | t]) - num)
    if num2 == 0 do
      {Lists.take([h| t], num2), Lists.drop([h | t], num)}
    else
      {Lists.take([h | t], num2-1), Lists.drop([h | t], num)}
    end
  end

  def compress([]) do [] end

  def compress(movements) do
    compressed = rules(movements)
    if compressed == movements do movements
    else compress(compressed) end
  end

  def rules([]) do [] end

  def rules([{_, 0} | []]) do
    []
  end

  def rules([xs | []]) do
    [xs]
  end

  def rules([{_, 0} | t]) do
    rules(t)
  end

  def rules([{t1, o1},{_, 0} | t]) do
    rules([{t1, o1} | t])
  end

  def rules([{t1, o1},{t1, o2} | t])do
    rules([{t1, o1 + o2} | t])
  end

  def rules([{t1, o1}, {t2, o2} | t])do
      [{t1, o1} | rules([{t2, o2} | t])]
  end

end
