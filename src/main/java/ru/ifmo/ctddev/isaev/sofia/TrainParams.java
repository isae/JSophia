package ru.ifmo.ctddev.isaev.sofia;

/**
 * @author iisaev
 */
public class TrainParams {
    private final StringBuilder argsBuilder = new StringBuilder();

    private TrainParams addArgument(String argName, String argValue) {
        argsBuilder.append(argName).append(" ").append(argValue);
        return this;
    }

    public TrainParams() {
        argsBuilder.append("--return_model");
        argsBuilder.append("--stdin_train_data");
    }

    public TrainParams randomSeed(long randomSeed) {
        return addArgument("--random_seed", String.valueOf(randomSeed));
    }

    public TrainParams dimensionality(long dimensionality) {
        return addArgument("--dimensionality", String.valueOf(dimensionality));
    }

    public TrainParams hashMaskBits(long hashMaskBits) {
        return addArgument("--hash_mask_bits", String.valueOf(hashMaskBits));
    }

    public TrainParams noBiasTerm() {
        return addArgument("--no_bias_term", "");
    }

    public TrainParams lambda(double lambda) {
        return addArgument("--lambda", String.valueOf(lambda));
    }

    public TrainParams rankStepProbability(double rankStepProbability) {
        return addArgument("--rank_step_probability", String.valueOf(rankStepProbability));
    }

    public TrainParams passiveAggressiveMaxSize(double passiveAggressiveMaxSize) {
        return addArgument("--passive_aggressive_c", String.valueOf(passiveAggressiveMaxSize));
    }

    public TrainParams passiveAggressiveLambda(double passiveAggressiveLambda) {
        return addArgument("--passive_aggressive_lambda", String.valueOf(passiveAggressiveLambda));
    }

    public TrainParams perceptronMarginSize(double perceptronMarginSize) {
        return addArgument("--perceptron_margin_size", String.valueOf(perceptronMarginSize));
    }

    public TrainParams iterations(long iterations) {
        return addArgument("--iterations", String.valueOf(iterations));
    }

    public TrainParams learnerType(LearnerType learnerType) {
        return addArgument("--learner_type", learnerType.getArg());
    }

    public TrainParams etaType(EtaType etaType) {
        return addArgument("--eta_type", etaType.getArg());
    }

    public TrainParams loopType(LoopType loopType) {
        return addArgument("--loop_type", loopType.getArg());
    }

    public TrainParams predictionType(PredictionType predictionType) {
        return addArgument("--prediction_type", predictionType.getArg());
    }

    @Override
    public String toString() {
        return argsBuilder.toString();
    }
}
