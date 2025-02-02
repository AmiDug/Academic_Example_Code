from matchpredictor.matchresults.result import Fixture, Outcome
from matchpredictor.predictors.predictor import Prediction, Predictor


class AlphabetPredictor(Predictor):
    def predict(self, fixture: Fixture) -> Prediction:
        alphabetTeam = min(fixture.home_team.name, fixture.away_team.name)
        if (alphabetTeam == fixture.home_team.name):
            outcome = Outcome.HOME
        else:
            outcome = Outcome.AWAY
        return Prediction(outcome)
