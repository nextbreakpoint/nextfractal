fractal {
	orbit [-1 - 1i,+1 + 1i] [z,x,n] {
		trap trap1 [1] {
			MOVETO(1);
			LINETO(1.1-0.1i);
			LINETO(1.1+0.1i);
			LINETO(1);
		}
		loop [0, 2] (|z| > 4 & trap1[z]) {
			y = 0;
			t = 3;
			x = t + 4 + 1i;
			k = t + 4;
			z = x * (y + 5i);
			t = |z|;
		}
	} color [#FF000000] {
		palette palette1 {
			[#000000 > #FFFFFF, 100];
			[#FFFFFF > #FF0000, 100];
		}
		rule (re(n) = 0) [0.5] {
			|x|,5,5,5
		}
		rule (re(n) > 0) [0.5] {
			palette1[re(n)]
		}
	}
}
