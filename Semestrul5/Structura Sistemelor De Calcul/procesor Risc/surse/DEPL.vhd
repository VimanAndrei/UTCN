library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use WORK.RISC_pkg.ALL;


entity DEPL is
    Port ( D   : in  STD_LOGIC_VECTOR (31 downto 0);
           Sel : in  STD_LOGIC_VECTOR (3 downto 0);
           Sh  : in  STD_LOGIC_VECTOR (4 downto 0);
           SRI : in  STD_LOGIC;
           SLI : in  STD_LOGIC;
           G   : out STD_LOGIC_VECTOR (31 downto 0));
end DEPL;

architecture Behavioral of DEPL is
    signal SelMx : STD_LOGIC_VECTOR (1 downto 0) := "00";       -- selectie multiplexoare
begin
    selMx <= "01" when (Sel = FSHR) and (Sh /= "00000") else
             "10" when (Sel = FSHL) and (Sh /= "00000") else
             "00";                                              -- inclusiv daca Sh = "00000"

    MUX31_i:    entity WORK.mux_4_1 port map (I0 => D(31),      -- bitul 31
                    I1 => SRI, I2 => D(30), I3 => '0', Sel => SelMx, Z => G(31));

    genMUX:     for i in 30 downto 1 generate
    uMUX30_1:       entity WORK.mux_4_1 port map (I0 => D(i),   -- bitii 30..1
                        I1 => D(i+1), I2 => D(i-1), I3 => '0', Sel => SelMx, Z => G(i));
    end generate genMUX;

    MUX0_i:     entity WORK.mux_4_1 port map (I0 => D(0),       -- bitul 0
                    I1 => D(1), I2 => SLI, I3 => '0', Sel => SelMx, Z => G(0));
		
end Behavioral;
