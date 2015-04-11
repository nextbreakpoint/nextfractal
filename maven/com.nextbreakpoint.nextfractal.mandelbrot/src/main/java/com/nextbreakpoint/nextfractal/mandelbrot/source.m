fractal {
	orbit [<-2.5,-1.5>,<0.5,1.5>] [z,n] {
		begin {
			z = x;
		}
		loop [0, 200] (mod2(z) > 4) {
			z = z * z + w;
		}
	}
	color [(1,0,0,0)] {
		rule (re(n) = 0) [1.0] {
			1, // alpha
			0, // red
			0, // green
			0  // blue
		}
		rule (re(n) > 0) [1.0] {
			1, // alpha
			1, // red
			1, // green
			1  // blue
		}
	}
}
