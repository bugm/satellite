
package satellite;

public class predicate {
	        static double de2ra=1.74532925E-2;
			static double pi     =  3.1415926535898;
			static double pio2   =  1.5707963267949;
			static double x3pio2 =  4.71238898;
			static double twopi  =  6.2831853071796;
			static double e6a    =  1.0E-6;
			static double tothrd =  6.6666667E-1;
			static double xj2    =  1.0826158E-3;
			static double xj3    = -2.53881E-6;
			static double xj4    = -1.65597E-6;
			static double xke    =  7.43669161E-2;
			static double xkmper =  6.378135E3;
			static double xmnpda =  1.44E3;
			static double ae     =  1.0;
			static double ck2    =  5.413079E-4;
			static double ck4    =  6.209887E-7;
			static double f      =  3.352779E-3;
			static double ge     =  3.986008E5;
			static double s      =  1.012229;
			static double qoms2t =  1.880279E-09;
			static double secday  = 8.6400E4;
			static double omega_E = 1.0027379;
			static double omega_ER =6.3003881;
			static double zns      =1.19459E-5;
			static double c1ss     =2.9864797E-6;
			static double zes      =0.01675;
			static double znl      =1.5835218E-4;
			static double c1l      =4.7968065E-7;
			static double zel      =0.05490;
			static double zcosis   =0.91744867;
			static double zsinis   =0.39785416;
			static double zsings  =-0.98088458;
			static double zcosgs   =0.1945905;
			static double zcoshs   =1;
			static double zsinhs   =0;
			static double q22      =1.7891679E-6;
			static double q31      =2.1460748E-6;
			static double q33      =2.2123015E-7;
			static double g22      =5.7686396;
			static double g32      =0.95240898;
			static double g44      =1.8014998;
			static double g52      =1.0508330;
			static double g54      =4.4108898;
			static double root22   =1.7891679E-6;
			static double root32   =3.7393792E-7;
			static double root44   =7.3636953E-9;
			static double root52   =1.1428639E-7;
			static double root54   =2.1765803E-9;
			static double thdt     =4.3752691E-3;
			static double rho      =1.5696615E-1;
			
			
			  
			 public position SGP4(double tsince, double xndt2o,double xndd6o,double bstar,double xincl,double xnodeo,double eo,double omegao,double xmo,double xno)
			  {
				 boolean simple_flag = false;
				 
			    double
			  	aodp,aycof,c1,c4,c5,cosio,d2,d3,d4,delmo,omgcof,
			  	eta,omgdot,sinio,xnodp,sinmo,t2cof,t3cof,t4cof,t5cof,
			  	x1mth2,x3thm1,x7thm1,xmcof,xmdot,xnodcf,xnodot,xlcof;

			    double
			  	cosuk,sinuk,rfdotk,vx,vy,vz,ux,uy,uz,xmy,xmx,
			  	cosnok,sinnok,cosik,sinik,rdotk,xinck,xnodek,uk,
			  	rk,cos2u,sin2u,u,sinu,cosu,betal,rfdot,rdot,r,pl,
			  	elsq,esine,ecose,epw,cosepw,x1m5th,xhdot1,tfour,
			  	sinepw,capu,ayn,xlt,aynl,xll,axn,xn,beta,xl,e,a,
			  	tcube,delm,delomg,templ,tempe,tempa,xnode,tsq,xmp,
			  	omega,xnoddf,omgadf,xmdf,a1,a3ovk2,ao,betao,betao2,
			  	c1sq,c2,c3,coef,coef1,del1,delo,eeta,eosq,etasq,
			  	perige,pinvsq,psisq,qoms24,s4,temp,temp1,temp2,
			  	temp3,temp4,temp5,temp6,theta2,theta4,tsi;

			    int i;

			 
			  	a1 = Math.pow(xke/xno,tothrd);
			  	cosio = Math.cos(xincl);
			  	theta2 = cosio*cosio;
			  	x3thm1 = 3*theta2-1.0;
			  	eosq = eo*eo;
			  	betao2 = 1-eosq;
			  	betao = Math.sqrt(betao2);
			  	del1 = 1.5*ck2*x3thm1/(a1*a1*betao*betao2);
			  	ao = a1*(1-del1*(0.5*tothrd+del1*(1+134/81*del1)));
			  	delo = 1.5*ck2*x3thm1/(ao*ao*betao*betao2);
			  	xnodp = xno/(1+delo);
			  	aodp = ao/(1-delo);

			  	/* For perigee less than 220 kilometers, the "simple" flag is set */
			  	/* and the equations are truncated to linear variation in sqrt a  */
			  	/* and quadratic variation in mean anomaly.  Also, the c3 term,   */
			  	/* the delta omega term, and the delta m term are dropped.        */
			  	if((aodp*(1-eo)/ae) < (220/xkmper+ae))
			  	  simple_flag = false;
			  	else
			  	  simple_flag = true;

			  	/* For perigee below 156 km, the       */
			  	/* values of s and qoms2t are altered. */
			  	s4 = s;
			  	qoms24 = qoms2t;
			  	perige = (aodp*(1-eo)-ae)*xkmper;
			  	if(perige < 156)
			  	{
			  	  if(perige <= 98)
			  		s4 = 20;
			  	  else
			  		s4 = perige-78;
			  	  qoms24 = Math.pow((120-s4)*ae/xkmper,4);
			  	  s4 = s4/xkmper+ae;
			  	}; /* End of if(perige <= 98) */

			  	pinvsq = 1/(aodp*aodp*betao2*betao2);
			  	tsi = 1/(aodp-s4);
			  	eta = aodp*eo*tsi;
			  	etasq = eta*eta;
			  	eeta = eo*eta;
			  	psisq = Math.abs(1-etasq);
			  	coef = qoms24*Math.pow(tsi,4);
			  	coef1 = coef/Math.pow(psisq,3.5);
			  	c2 = coef1*xnodp*(aodp*(1+1.5*etasq+eeta*(4+etasq))+
			  		0.75*ck2*tsi/psisq*x3thm1*(8+3*etasq*(8+etasq)));
			  	c1 = bstar*c2;
			  	sinio = Math.sin(xincl);
			  	a3ovk2 = -xj3/ck2*Math.pow(ae,3);
			  	c3 = coef*tsi*a3ovk2*xnodp*ae*sinio/eo;
			  	x1mth2 = 1-theta2;
			  	c4 = 2*xnodp*coef1*aodp*betao2*(eta*(2+0.5*etasq)+
			  		eo*(0.5+2*etasq)-2*ck2*tsi/(aodp*psisq)*
			  		(-3*x3thm1*(1-2*eeta+etasq*(1.5-0.5*eeta))+0.75*
			  		 x1mth2*(2*etasq-eeta*(1+etasq))*Math.cos(2*omegao)));
			  	c5 = 2*coef1*aodp*betao2*(1+2.75*(etasq+eeta)+eeta*etasq);
			  	theta4 = theta2*theta2;
			  	temp1 = 3*ck2*pinvsq*xnodp;
			  	temp2 = temp1*ck2*pinvsq;
			  	temp3 = 1.25*ck4*pinvsq*pinvsq*xnodp;
			  	xmdot = xnodp+0.5*temp1*betao*x3thm1+
			  	  0.0625*temp2*betao*(13-78*theta2+137*theta4);
			  	x1m5th = 1-5*theta2;
			  	omgdot = -0.5*temp1*x1m5th+0.0625*temp2*(7-114*theta2+
			  		395*theta4)+temp3*(3-36*theta2+49*theta4);
			  	xhdot1 = -temp1*cosio;
			  	xnodot = xhdot1+(0.5*temp2*(4-19*theta2)+
			  		2*temp3*(3-7*theta2))*cosio;
			  	omgcof =bstar*c3*Math.cos(omegao);
			  	xmcof = -tothrd*coef*bstar*ae/eeta;
			  	xnodcf = 3.5*betao2*xhdot1*c1;
			  	t2cof = 1.5*c1;
			  	xlcof = 0.125*a3ovk2*sinio*(3+5*cosio)/(1+cosio);
			  	aycof = 0.25*a3ovk2*sinio;
			  	delmo = Math.pow(1+eta*Math.cos(xmo),3);
			  	sinmo = Math.sin(xmo);
			  	x7thm1 = 7*theta2-1;

			    /* Update for secular gravity and atmospheric drag. */
			    xmdf = xmo+xmdot*tsince;
			    omgadf = omegao+omgdot*tsince;
			    xnoddf = xnodeo+xnodot*tsince;
			    omega = omgadf;
			    xmp = xmdf;
			    tsq = tsince*tsince;
			    xnode = xnoddf+xnodcf*tsq;
			    tempa = 1-c1*tsince;
			    tempe = bstar*c4*tsince;
			    templ = t2cof*tsq;
			    if (!simple_flag)
			    {
			  	  c1sq = c1*c1;
			  	  d2 = 4*aodp*tsi*c1sq;
			  	  temp = d2*tsi*c1/3;
			  	  d3 = (17*aodp+s4)*temp;
			  	  d4 = 0.5*temp*aodp*tsi*(221*aodp+31*s4)*c1;
			  	  t3cof = d2+2*c1sq;
			  	  t4cof = 0.25*(3*d3+c1*(12*d2+10*c1sq));
			  	  t5cof = 0.2*(3*d4+12*c1*d3+6*d2*d2+15*c1sq*(2*d2+c1sq));
			  	delomg = omgcof*tsince;
			  	delm = xmcof*(Math.pow(1+eta*Math.cos(xmdf),3)-delmo);
			  	temp = delomg+delm;
			  	xmp = xmdf+temp;
			  	omega = omgadf-temp;
			  	tcube = tsq*tsince;
			  	tfour = tsince*tcube;
			  	tempa = tempa-d2*tsq-d3*tcube-d4*tfour;
			  	tempe = tempe+bstar*c5*(Math.sin(xmp)-sinmo);
			  	templ = templ+t3cof*tcube+tfour*(t4cof+tsince*t5cof);
			    }/* End of if (isFlagClear(SIMPLE_FLAG)) */

			    a = aodp*Math.pow(tempa,2);
			    e = eo-tempe;
			    xl = xmp+omega+xnode+xnodp*templ;
			    beta = Math.sqrt(1-e*e);
			    xn = xke/Math.pow(a,1.5);

			    /* Long period periodics */
			    axn = e*Math.cos(omega);
			    temp = 1/(a*beta*beta);
			    xll = temp*xlcof*axn;
			    aynl = temp*aycof;
			    xlt = xl+xll;
			    ayn = e*Math.sin(omega)+aynl;

			    /* Solve Kepler's' Equation */
			    capu = FMod2p(xlt-xnode);
			    temp2 = capu;

			    i = 0;
			    do
			    {
			  	sinepw = Math.sin(temp2);
			  	cosepw = Math.cos(temp2);
			  	temp3 = axn*sinepw;
			  	temp4 = ayn*cosepw;
			  	temp5 = axn*cosepw;
			  	temp6 = ayn*sinepw;
			  	epw = (capu-temp4+temp3-temp2)/(1-temp5-temp6)+temp2;
			  	if(Math.abs(epw-temp2) <= e6a)
			  	  break;
			  	temp2 = epw;
			    }
			    while( i++ < 10 );

			    /* Short period preliminary quantities */
			    ecose = temp5+temp6;
			    esine = temp3-temp4;
			    elsq = axn*axn+ayn*ayn;
			    temp = 1-elsq;
			    pl = a*temp;
			    r = a*(1-ecose);
			    temp1 = 1/r;
			    rdot = xke*Math.sqrt(a)*esine*temp1;
			    rfdot = xke*Math.sqrt(pl)*temp1;
			    temp2 = a*temp1;
			    betal = Math.sqrt(temp);
			    temp3 = 1/(1+betal);
			    cosu = temp2*(cosepw-axn+ayn*esine*temp3);
			    sinu = temp2*(sinepw-ayn-axn*esine*temp3);
			    u = AcTan(sinu, cosu);
			    sin2u = 2*sinu*cosu;
			    cos2u = 2*cosu*cosu-1;
			    temp = 1/pl;
			    temp1 = ck2*temp;
			    temp2 = temp1*temp;

			    /* Update for short periodics */
			    rk = r*(1-1.5*temp2*betal*x3thm1)+0.5*temp1*x1mth2*cos2u;
			    uk = u-0.25*temp2*x7thm1*sin2u;
			    xnodek = xnode+1.5*temp2*cosio*sin2u;
			    xinck = xincl+1.5*temp2*cosio*sinio*cos2u;
			    rdotk = rdot-xn*temp1*x1mth2*sin2u;
			    rfdotk = rfdot+xn*temp1*(x1mth2*cos2u+1.5*x3thm1);

			    /* Orientation vectors */
			    sinuk = Math.sin(uk);
			    cosuk = Math.cos(uk);
			    sinik = Math.sin(xinck);
			    cosik = Math.cos(xinck);
			    sinnok = Math.sin(xnodek);
			    cosnok = Math.cos(xnodek);
			    xmx = -sinnok*cosik;
			    xmy = cosnok*cosik;
			    ux = xmx*sinuk+cosnok*cosuk;
			    uy = xmy*sinuk+sinnok*cosuk;
			    uz = sinik*sinuk;
			    vx = xmx*cosuk-cosnok*sinuk;
			    vy = xmy*cosuk-sinnok*sinuk;
			    vz = sinik*cosuk;

			    /* Position and velocity */
			    position p = new position();
			    p.setX(rk*ux); 
			    p.setY(rk*uy);
			    p.setZ(rk*uz);
			    return p;
//			    vel->x = rdotk*ux+rfdotk*vx;
//			    vel->y = rdotk*uy+rfdotk*vy;
//			    vel->z = rdotk*uz+rfdotk*vz;

			  } /*SGP4*/
			
public double FMod2p( double x )
			  {
			    int i;
			    double ret_val;

			    ret_val = x;
			    i = (int)(ret_val / twopi);
			    ret_val -= (double)i * twopi;
			    if (ret_val < 0) ret_val += twopi;

			    return (ret_val);
			  }

public double AcTan(double sinx, double cosx)
{
if(cosx == 0)
{
	if(sinx > 0)
	  return (pio2);
	else
	  return (x3pio2);
}
else
{
	if(cosx > 0)
	{
	  if(sinx > 0)
		return ( Math.atan(sinx/cosx) );
	  else
		return ( twopi + Math.atan(sinx/cosx) );
	}
	else
	  return ( pi + Math.atan(sinx/cosx) );
}

}

	
	
}
