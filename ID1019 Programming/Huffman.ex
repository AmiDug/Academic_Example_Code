defmodule Huffman do
  def sample do
        'the quick brown fox jumps over the lazy dog
        this is a sample text that we will use when we build
         up a table we will only handle lower case letters and
        no punctuation symbols the frequency will of course not
        represent english but it is probably not that far off'
  end

  def text() do
    'this is something that we should encode'
  end

  def bench(file, n) do
    {text, b} = read(file, n)
    c = length(text)
    {tree, t2} = time(fn -> tree(text) end)
    {encode, t3} = time(fn -> encode_table(tree) end)
    s = length(encode)
    {decode, t6} = time(fn -> decode_table(tree) end)
    {encoded, t5} = time(fn -> encode(text, encode) end)
    e = div(length(encoded), 8)
    r = Float.round(e / b, 3)
    {_, t6} = time(fn -> decode(encoded, decode) end)

    #IO.puts("text of #{c} characters")
    #IO.puts("tree built in #{t2} ms")
    #IO.puts("table encoded in #{t3} ms")
    #IO.puts("table decoded in #{t6} ms")
    IO.puts("encoded in #{t5} ms")
    IO.puts("decoded in #{t6} ms")
    #IO.puts("source #{b} bytes, encoded #{e} bytes, compression #{r}")
  end

  def time(func) do
    initial = Time.utc_now()
    result = func.()
    final = Time.utc_now()
    {result, Time.diff(final, initial, :microsecond) / 1000}
  end

  def read(file, n) do
   {:ok, fd} = File.open(file, [:read])
    binary = IO.read(fd, n)
    File.close(fd)

    length = byte_size(binary)
    case :unicode.characters_to_list(binary, :utf8) do
      {:incomplete, chars, rest} ->
        {chars, length - byte_size(rest)}
      chars ->
        {chars, length}
    end
  end

  def test do
    sample = sample()
    text = text()
    tree = tree(sample)
    encode = encode_table(tree)
    seq = encode(text, encode)
    decode = decode_table(tree)
    decode(seq, decode)
  end

  def tree(sample) do
    freq = freq(sample)
    huffman(freq)
  end

  def freq(sample), do: freq(sample, [])

  def freq([], freq) do freq end

  def freq([char | rest], freq) do
    freq(rest, freq_count(char, freq))
  end

  def freq_count(char, []), do: [{char, 1}]

  def freq_count(char, [{char, n} | freq]) do
    [{char, n + 1} | freq]
  end

  def huffman(freq) do
    sorted = List.keysort(freq, 1)
    huffman_tree(sorted)
  end

  def huffman_tree([{tree, _}]) do tree end
  def huffman_tree([{x, x2}, {y, y2} | rest]) do
    huffman_tree(insert({{x, y}, x2 + y2}, rest))
  end

  def insert({x, x2}, []) do [{x, x2}] end
  def insert({x, x2}, [{y, y2} | rest]) when x2 < y2 do
    [{x, x2}, {y, y2} | rest]
  end
  def insert({x, x2}, [{y, y2} | rest]) do
    [{y, y2} | insert({x, x2}, rest)]
  end

  def encode_table(tree) do coding(tree, []) end

  def coding({x, y}, seq) do
    x2 = coding(x, [0 | seq])
    y2 = coding(y, [1 | seq])
    x2 ++ y2
  end
  def coding(x, seq) do
    [{x, Enum.reverse(seq)}]
  end

  def encode([], _) do [] end
  def encode([char|rest], table) do
    {_, code} = List.keyfind(table, char, 0)
    code ++ encode(rest, table)
  end

  def decode_table(tree) do coding(tree, []) end

  def decode([], _) do [] end
  def decode(seq, table) do
    {char, rest} = decode_char(seq, 1, table)
    [char | decode(rest, table)]
  end
  def decode_char(seq, n, table) do
    {code, rest} = Enum.split(seq, n)
    case List.keyfind(table, code, 1) do
      {char, _} ->
        {char, rest}
      nil ->
        decode_char(seq, n+1, table)
    end
  end
  def read() do
    file = "D:/BansheeBomb/Downloads/tomcats/kallocain.txt"
    {:ok, file} = File.open(file, [:read])
    binary = IO.read(file, :all)
    File.close(file)

    case :unicode.characters_to_list(binary, :utf8) do
      {:incomplete, list, _} ->
        list;
      list ->
        list
    end
  end
end
