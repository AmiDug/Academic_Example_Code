defmodule Cmplx do

  def new(r, i) do
    {r, i}
  end

  def add(a, b) do
    {a1, b1} = a
    {a2, b2} = b
    {a1+a2, b1+b2}
  end

  def sqr({r,i}) do
    remainder = r * r + (-1 * i * i)
    immediate = 2 * r * i
    {remainder, immediate}
  end

  def abs({r,i}) do
    :math.sqrt(r * r + i * i)
  end
end

defmodule Brot do

  def mandelbrot(c, m) do
    z0 = Cmplx.new(0, 0)
    i = 0
    test(i, z0, c, m)
  end

  def test(m, _, _, m) do 0 end
  def test(i, z, c, m) do
    abs = Cmplx.abs(z)
     if abs <= 2 do
        z1 = Cmplx.add(Cmplx.sqr(z), c)
        test(i + 1, z1, c, m)
     else i end
  end

end

defmodule PPM do

  def write(name, image) do
    height = length(image)
    width = length(List.first(image))
    {:ok, fd} = File.open(name, [:write])
    IO.puts(fd, "P6")
    IO.puts(fd, "#generated by ppm.ex")
    IO.puts(fd, "#{width} #{height}")
    IO.puts(fd, "255")
    rows(image, fd)
    File.close(fd)
  end

  defp rows(rows, fd) do
    Enum.each(rows, fn(r) ->
      colors = row(r)
      IO.write(fd, colors)
    end)
  end

  defp row(row) do
    List.foldr(row, [], fn({:rgb, r, g, b}, a) ->
      [r, g, b | a]
    end)
  end

end

defmodule Colors do
  def convert(depth, max) do
    a = depth/max
    b = a * 4
    x = trunc(b)
    y = trunc(255*(b - x))
    case x do
      0 ->
        {:rgb, y, 0, 0}
      1 ->
        {:rgb, 255, y, 0}
      2 ->
        {:rgb, 255-y, 255, 0}
      3 ->
        {:rgb, 0, 255, y}
      4 ->
        {:rgb, 0, 255-y, 255}
    end
  end
end


defmodule Mandel do

  def mandelbrot(w, h, x, y, k, depth) do
    trans = fn(w, h) ->
    Cmplx.new(x + k * (w - 1), y - k * (h - 1))
    end
   rows(w, h, trans, depth, [])
  end

  def rows(_, 0, _, _, rows) do rows end

  def rows(w, h, trans, max, rows) do
    row = row(w, h, trans, max, [])
    rows(w, h - 1, trans, max, [row|rows])
  end

  def row(0,_h ,_tr ,_max, row) do row end

  def row(w, h, f, max, row) do
    c = f.(w, h)
    depth = Brot.mandelbrot(c, max)
    color = Colors.convert(depth, max)
    row(w - 1, h, f, max, [color | row])
  end

  def demo() do
    small(-2.6, 1.2, 1.2)
  end
  def small(x0, y0, xn) do
    width = 960
    height = 540
    depth = 64
    k = (xn - x0) / width
    image = Mandel.mandelbrot(width, height, x0, y0, k, depth)
    PPM.write("small.ppm", image)
  end
end
