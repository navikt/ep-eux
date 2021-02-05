# ep-eux

## Beskrivelse
Enkelt bibliotek for å forenkle påkobling mot Rina EUX API, samt redusere duplikatkode.

| Miljø | URL |
| ----- | --- |
| Q1 | https://eux-rina-api-q1.nais.preprod.local/swagger-ui.html |
| Q2 | https://eux-rina-api-q2.nais.preprod.local/swagger-ui.html |

For å åpne lenkene er du nødt til å gå via Utviklerimage eller Kodeverksklienten.  
Kan alternativt bruke port-forward for å gjøre de tilgjengelige på localhost.

## Releasing

This library is released using the `net.researchgate/gradle-release`-plugin.

## Oppdatere avhengigheter

Sjekke om man har utdaterte avhengigheter (forsøker å unngå milestones og beta-versjoner):

```
./gradlew dependencyUpdates
```

Dersom du er supertrygg på testene kan du forsøke en oppdatering av alle avhengighetene:

```
./gradlew useLatestVersions && ./gradlew useLatestVersionsCheck
```

## OWASP avhengighetssjekk

(Pass på at du kan nå `ossindex.sonatype.org` og `nvd.nist.gov` gjennom evt proxy e.l.) 

```
./gradlew dependencyCheckAnalyze && open build/reports/dependency-check-report.html
```

## Snyk CLI

Siden Snyk ikke støtter Gradle sin Kotlin DSL må sjekker kjøres fra kommandolinjen.
Se: https://support.snyk.io/hc/en-us/articles/360003812458-Getting-started-with-the-CLI
