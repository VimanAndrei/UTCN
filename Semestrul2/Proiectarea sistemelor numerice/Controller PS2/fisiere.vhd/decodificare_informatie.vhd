library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;

entity decodificare_informatie is  
	port (
	key1: in std_logic_vector(7 downto 0);
	key2: in std_logic_vector(7 downto 0);
	key3: in std_logic_vector(7 downto 0);
	clr:in std_logic;
	iesi0: out std_logic_vector(6 downto 0); 
	iesi1: out std_logic_vector(6 downto 0);
	iesi2: out std_logic_vector(6 downto 0);
	iesi3: out std_logic_vector(6 downto 0)
	);	
end decodificare_informatie;  


architecture decodificare_informatie of decodificare_informatie is
signal y0,y1,y2,y3,a_to_g: std_logic_vector(6 downto 0); 
signal contor: std_logic_vector(1 downto 0);
begin
	dd:process(key1,key2,key3,clr)	
	begin	
		if (clr = '1') or (key1 = "01011010")  then
			y0 <= (others => '1');
			y1 <= (others => '1');
			y2 <= (others => '1');
			y3 <= (others => '1');
			contor <= (others => '0');
		elsif key2 = "11110000" then
			if key1 = "01110101" or key1 = "01101011" then 
				contor<=contor+1;
			else if key1 = "01110100" or key1 = "01110010" then 
				contor <= contor - 1;  
			else
				case key1 is
			when "01000101" => a_to_g <= "0000001";--0
			when "00010110" => a_to_g <= "1001111";--1
			when "00011110" => a_to_g <= "0010010";--2
			when "00100110" => a_to_g <= "0000110";--3
			when "00100101" => a_to_g <= "1001100";--4
			when "00101110" => a_to_g <= "0100100";--5
			when "00110110"	=> a_to_g <= "0100000";--6
			when "00111101" => a_to_g <= "0001101";--7
			when "00111110"	=> a_to_g <= "0000000";--8
			when "01000110"	=> a_to_g <= "0000100";--9
			when "00011100"	=> a_to_g <= "0001000";--A
			when "00110010"	=> a_to_g <= "1100000";--b	
			when "00100001"	=> a_to_g <= "0110001";--C
			when "00100011"	=> a_to_g <= "1000010";--d
			when "00100100"	=> a_to_g <= "0110000";--E
			when "00101011"	=> a_to_g <= "0111000";--F	
			when "00110011" => a_to_g <= "1001000";--H
			when "01000011" => a_to_g <= "1111001";--I
			when "00111011" => a_to_g <= "1000011";--J 
			when "01001011"	=> a_to_g <= "1110001";--L
			when "00110001"	=> a_to_g <= "1101010";--n
			when "01000100"	=> a_to_g <= "1100010";--o 
			when "01001101"	=> a_to_g <= "0011000";--P
			when "00011011" => a_to_g <= "0100100";--S
			when "00101100" => a_to_g <= "1110000";--t
			when "00111100" => a_to_g <= "0100100";--U
			when others => a_to_g <= "0000000";
			end case;
			
			if contor = "11" then 
				y3<=a_to_g;
			elsif contor = "10"then	 
				y3<=y2;
				y2<=a_to_g;
			elsif contor = "01" then
				y3<=y2;
				y2<=y1;
				y1<=a_to_g;	 
			else
				y3<=y2;
				y2<=y1;
			    y1<=y0;
				y0<=a_to_g;	 
			end if;
				
		  end if;
			
		end if;
	end if;
	end process dd;
	iesi0 <= y0; 
	iesi1 <= y1;
	iesi2 <= y2;
	iesi3 <= y3; 
end decodificare_informatie;
	
	
	
	
			
			


