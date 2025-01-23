defmodule Moves do

  def move([], states) do [states] end

  def move([h | t], states) do
        [states | move(t, single(h, states))]
  end

  def single({:one, movements},{main, one, two}) do
    cond do
      movements < 0 ->
        transit = Lists.take(one, -1 * movements)
        one = Lists.drop(one, -1 * movements)
        main = Lists.append(main, transit)
        {main, one, two}
      movements > 0 ->
        transit = Lists.drop(main, Enum.count(main) - movements)
        one = Lists.append(transit, one)
        main = Lists.take(main, Enum.count(main) - movements)
        {main, one, two}
      movements == 0 ->
        {main, one, two}
    end
  end

  def single({:two, movements},{main, one, two}) do
    cond do
      movements < 0 ->
        transit = Lists.take(two,-1 * movements)
        two = Lists.drop(two, -1 * movements)
        main = Lists.append(main, transit)
        {main, one, two}
      movements > 0 ->
        transit = Lists.drop(main, Enum.count(main) - movements)
        two = Lists.append(transit, two)
        main = Lists.take(main, Enum.count(main) - movements)
        {main, one, two}
      movements == 0 ->
        {main, one, two}
    end
  end
end
