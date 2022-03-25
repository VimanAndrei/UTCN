library ieee;
use ieee.std_logic_1164.all; 
use ieee.std_logic_unsigned.all;
entity decoder is 
	 port (
	 decodein:in std_logic_vector(1 downto 0);
	 decodeout: out std_logic_vector(3 downto 0)
	 );
end decoder;  

architecture decoder of decoder is
begin
	p1:process(decodein)
	begin 
		case decodein is
			when "00" => decodeout<="1110";
			when "01" => decodeout<="1101";	
			when "10" => decodeout<="1011";
			when "11" => decodeout<="0111"; 
			when others => decodeout<="1110";
		end case;
	end process p1;
end decoder;
