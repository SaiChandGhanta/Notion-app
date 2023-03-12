FROM justb4/jmeter:latest

COPY entrypoint.sh /

RUN chmod +x /entrypoint.sh

CMD ["-n", "-t TodoTestPlan.jmx", "-l TodoTestPlan.jtl", "-j jmeter.log","-e","-o reports/"]

ENTRYPOINT [ "/entrypoint.sh" ]


