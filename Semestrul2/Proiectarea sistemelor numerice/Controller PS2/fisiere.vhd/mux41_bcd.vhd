library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
entity mux41 is
	port (
	amux41: in std_logic_vector(27 downto 0);
	smux41: in std_logic_vector(1 downto 0);
	ymux41: out std_logic_vector(6 downto 0)
	);
end mux41;

architecture mux41 of mux41 is
begin
	p2:process(smux41,amux41)
	begin 
		case smux41 is
			when "00" => ymux41<=amux41(6 downto 0);
			when "01" => ymux41<=amux41(13 downto 7);	
			when "10" => ymux41<=amux41(20 downto 14);
			when "11" => ymux41<=amux41(27 downto 21);  
			when others => ymux41<=amux41(6 downto 0);
		end case;
	end process p2;
end mux41;

			