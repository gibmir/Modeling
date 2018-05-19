# Modeling 
Project is devoted to modeling of queuing systems. 
## Composition
* application - represent a application(like a: customers, sent messages e.t.c.);
* service channel - represent a service channel(like a: seller, cashier, physically existing channel);
* poster - special classes represent a modeling of queuing system(posting of applications by service channels);
* queuing system - special classes represent a results of modeling.
## Digital information transmission system:
In the system of digital information transfer speech is transmitted in 
digital form. Speech packets are transmitted through two transit channels,
buffered in drives before each channel. The transmission time of the packet
on the channel is 5 ms. The packets arrive after 6 ± 3 ms. Packets
transmitted more than 10 ms, at the output of the system are destroyed, 
as their appearance in the decoder will significantly reduce the quality
of the transmitted speech. Destruction of more than 30% of packages is
unacceptable. When this level is reached, the system accelerates the
transmission by 4 ms per channel by connecting resources. When the
level drops to an acceptable one, 