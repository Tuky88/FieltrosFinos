/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peea.mx.FF.formulas;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.formula.OperationEvaluationContext;
import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.formula.eval.EvaluationException;
import org.apache.poi.ss.formula.eval.NumberEval;
import org.apache.poi.ss.formula.eval.OperandResolver;
import org.apache.poi.ss.formula.eval.ValueEval;
import org.apache.poi.ss.formula.functions.FreeRefFunction;

/**
 *
 * @author Sistemas
 */
public class CalculateProm implements FreeRefFunction {

    @Override
    public ValueEval evaluate(ValueEval[] args, OperationEvaluationContext ec) {
        double principal, rate, years, result;
        try {
            if (args.length != 3) {
                return ErrorEval.VALUE_INVALID;
            }

            ValueEval v1 = OperandResolver.getSingleValue(args[0], ec.getRowIndex(), ec.getColumnIndex());
            ValueEval v2 = OperandResolver.getSingleValue(args[1], ec.getRowIndex(), ec.getColumnIndex());
            ValueEval v3 = OperandResolver.getSingleValue(args[2], ec.getRowIndex(), ec.getColumnIndex());
            principal = OperandResolver.coerceValueToDouble(v1);
            rate = OperandResolver.coerceValueToDouble(v2);
            years = OperandResolver.coerceValueToDouble(v3);

            result = calculateMortgagePayment(principal, rate, years);

            checkValue(result);

        } catch (EvaluationException e) {
            e.printStackTrace();
            return e.getErrorEval();
        }

        return new NumberEval(result);
    }

    public double calculateMortgagePayment(double p, double r, double y) {
        double i = r / 12;
        double n = y * 12;

        //M = P [ i(1 + i)n ] / [ (1 + i)n - 1] 
        double principalAndInterest
                = p * ((i * Math.pow((1 + i), n)) / (Math.pow((1 + i), n) - 1));

        return principalAndInterest;
    }

    /**
     * Excel does not support infinities and NaNs, rather, it gives a #NUM!
     * error in these cases
     *
     * @throws EvaluationException (#NUM!) if <tt>result</tt> is <tt>NaN</> or
     * <tt>Infinity</tt>
     */
    static final void checkValue(double result) throws EvaluationException {
        if (Double.isNaN(result) || Double.isInfinite(result)) {
            throw new EvaluationException(ErrorEval.NUM_ERROR);
        }
    }

}
