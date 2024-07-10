# Dog breeds

The project is implemented with Java/Spring (backend - `be` directory) and Angular (frontend - `fe` directory).

### How to run the project

Backend

In `be` directory run `gradlew bootRun`

Frontend

Change directory to `fe` and run following commands:

`npm i`

`ng serve`

### Background and Assumptions
- dog breeds api cache never expires 
- cache could be also populated on the beans init with @PostConstruct method, but the first call from the FE initializes
cache from API
- details by breed id: only data returned from get all breeds API method is only served (subBreeds, if any)
- CORS configuration is not suitable for production (CORS is switched off) for simplicity, for production further
frontend and backend configuration are required
- Analytics endpoint and menu items requires ADMIN role (username: admin, password: password)
- you can also log in as USER (username: user, password: password), but you'll can't access analytics
- it is better to place all BE endpoints under the `/api` path but requirements asked for endpoints without `/api' prefix
- `proxy.conf.json` could look much better if `/api` prefix path is used
- front end implementation based on PrimeNG admin template and PrimeNG components
- tests coverage on frontend can be better (template components and logic are not covered)
- analytics is not persisted and resets with every restart
