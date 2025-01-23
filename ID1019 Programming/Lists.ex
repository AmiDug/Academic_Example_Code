defmodule Lists do

  def take(_, 0) do [] end
  def take([h | t], n) do [h | take(t, n - 1)] end

  def drop(xs, 0) do xs end
  def drop([_ | t], n) do drop(t, n-1) end

  def append([], n) do n end
  def append([h | t], n) do [h | append(t, n)] end

  def member([], _) do :isnt end
  def member([h | _], h) do :is end
  def member([_ | t], n) do member(t, n) end

  def position([h | t], y) do position([h | t], y, 1) end
  def position([h | _], h, n) do n end
  def position([_ | t], y, n) do position(t, y, n + 1) end

end
