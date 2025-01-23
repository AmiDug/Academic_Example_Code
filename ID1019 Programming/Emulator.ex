defmodule Emulator do

    def run(prgm) do
        {code, data} = Program.load(prgm)
        out = Out.new()
        reg = Registers.new()
        run(0, code, reg, data, out)
    end

    def run(pc, code, reg, mem, out) do
        next = Program.read_instruction(code, pc)
        case next do
            :halt -> Out.close(out)

            {:out, rs} ->
              pc = pc + 4
              s = Register.read(reg, rs)
              out = Out.put(out, s)
              run(pc, code, reg, mem, out)

            {:add, rd, rs, rt} ->
              pc = pc + 4
              s = Register.read(reg, rs)
              t = Register.read(reg, rt)
              reg = Register.write(reg, rd, s + t)
              run(pc, code, reg, mem)

            {:sub, rd, rs, rt} ->
              pc = pc + 4
              s = Register.read(reg, rs)
              t = Register.read(reg, rt)
              reg = Register.write(reg, rd, s - t)
              run(pc, code, reg, mem)

            {:addi, rd, rt, imm} ->
              pc = pc + 4
              t = Register.read(reg, rt)
              reg = Register.write(reg, rd, t + imm)
              run(pc, code, reg, mem)

            {:lw, rd, rt, offset} ->
              pc = pc + 4
              t = Register.read(reg, rt)
              c = Program.read(mem, t + offset)
              reg = Register.write(reg, rd, c)
              run(pc, code, reg, mem)

            {:sw, rs, rt, offset} ->
              pc = pc + 4
              s = Register.read(reg, rs)
              t = Register.read(reg, rt)
              mem = Program.write(mem, s + offset, t, [])
              run(pc, code, reg, mem)

            {:beq, rs, rt, offset} ->
              s = Register.read(reg, rs)
              t = Register.read(reg, rt)
              cond do
                s == t ->
                  pc = Program.read(mem, adress)
                  run(pc, code, reg, mem)
                true ->
                  pc = pc + 4
                  run(pc, code, reg, mem)
            end
end

defmodule Program do

  def load(prgm) do
      {:prgm, code, data} = prgm
      {code, data}
  end

  def read([h|t], adress) do
    {{:label, lab}, {:word, _}} = h
    if lab == adress do x
    unless if [h|t] == [] do 0
    else read(t, adress)
    end
end

  def write([], adress, value, new) do
      new ++ [{{:label, adress}, {:word, value}}]
  end

  def write([h|t], adress, value, new) do
      {{:label, lab}, {:word, _}} = h
      cond do
          lab == adress ->
              new ++ [{{:label, lab}, {:word, value}}|t]
          true ->
              write(t, adress, value, new ++ [h])
      end
  end

  def read_instruction([h|t], pc) do
      cond do
          pc == 0 -> h
          pc != 0 -> read_instruction(t, pc - 4)
      end
  end
end



defmodule Out do

  def new() do
    []
  end

  def close(out) do
    IO.write("${out}")
  end

  def put(out,s) do
    [out | s]
  end
end

defmodule Register do
  def write(reg, val, imm) do
    put_elem(reg, val, imm)
  end
  def read(reg, val) do
    elem(reg, val)
  end
  def new() do
    [{:reg, 0, :zero}, {:reg, 1, 0}, {:reg, 2, 0}, {:reg, 3, 0}, {:reg, 4, 0}, {:reg, 5, 0}, {:reg, 6, 0}, {:reg, 7, 0}, {:reg, 8, 0}, {:reg, 9, 0}, {:reg, 10, 0}, :reg, {11, 0},
        {:reg, 12, 0}, {:reg, 13, 0}, {:reg, 14, 0}, {:reg, 15, 0}, {:reg, 16, 0}, {:reg, 17, 0}, {:reg, 18, 0}, {:reg, 19, 0}, {:reg, 20, 0}, {:reg, 21, 0}, {:reg, 22, 0},
        {:reg, 23, 0}, {:reg, 24, 0}, {:reg, 25, 0}, {:reg, 26, 0}, {:reg, 27, 0}, {:reg, 28, 0}, {:reg, 29, 0}, {:reg, 30, 0}, {:reg, 31, 0}, {:reg, 32, 0}]
  end
end
