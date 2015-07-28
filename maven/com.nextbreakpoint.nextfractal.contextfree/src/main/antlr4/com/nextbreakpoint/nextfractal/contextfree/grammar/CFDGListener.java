// Generated from com/nextbreakpoint/nextfractal/contextfree/grammar/CFDG.g4 by ANTLR 4.5.1
package com.nextbreakpoint.nextfractal.contextfree.grammar;

	import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CFDGParser}.
 */
public interface CFDGListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CFDGParser#choose}.
	 * @param ctx the parse tree
	 */
	void enterChoose(CFDGParser.ChooseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#choose}.
	 * @param ctx the parse tree
	 */
	void exitChoose(CFDGParser.ChooseContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#cfdg2}.
	 * @param ctx the parse tree
	 */
	void enterCfdg2(CFDGParser.Cfdg2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#cfdg2}.
	 * @param ctx the parse tree
	 */
	void exitCfdg2(CFDGParser.Cfdg2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#cfdg3}.
	 * @param ctx the parse tree
	 */
	void enterCfdg3(CFDGParser.Cfdg3Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#cfdg3}.
	 * @param ctx the parse tree
	 */
	void exitCfdg3(CFDGParser.Cfdg3Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#statement_v2}.
	 * @param ctx the parse tree
	 */
	void enterStatement_v2(CFDGParser.Statement_v2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#statement_v2}.
	 * @param ctx the parse tree
	 */
	void exitStatement_v2(CFDGParser.Statement_v2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#statement_v3}.
	 * @param ctx the parse tree
	 */
	void enterStatement_v3(CFDGParser.Statement_v3Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#statement_v3}.
	 * @param ctx the parse tree
	 */
	void exitStatement_v3(CFDGParser.Statement_v3Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#v3clues}.
	 * @param ctx the parse tree
	 */
	void enterV3clues(CFDGParser.V3cluesContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#v3clues}.
	 * @param ctx the parse tree
	 */
	void exitV3clues(CFDGParser.V3cluesContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#v2stuff}.
	 * @param ctx the parse tree
	 */
	void enterV2stuff(CFDGParser.V2stuffContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#v2stuff}.
	 * @param ctx the parse tree
	 */
	void exitV2stuff(CFDGParser.V2stuffContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#inclusion_v2}.
	 * @param ctx the parse tree
	 */
	void enterInclusion_v2(CFDGParser.Inclusion_v2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#inclusion_v2}.
	 * @param ctx the parse tree
	 */
	void exitInclusion_v2(CFDGParser.Inclusion_v2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#import_v3}.
	 * @param ctx the parse tree
	 */
	void enterImport_v3(CFDGParser.Import_v3Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#import_v3}.
	 * @param ctx the parse tree
	 */
	void exitImport_v3(CFDGParser.Import_v3Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#eof}.
	 * @param ctx the parse tree
	 */
	void enterEof(CFDGParser.EofContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#eof}.
	 * @param ctx the parse tree
	 */
	void exitEof(CFDGParser.EofContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#fileString}.
	 * @param ctx the parse tree
	 */
	void enterFileString(CFDGParser.FileStringContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#fileString}.
	 * @param ctx the parse tree
	 */
	void exitFileString(CFDGParser.FileStringContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#fileNameSpace}.
	 * @param ctx the parse tree
	 */
	void enterFileNameSpace(CFDGParser.FileNameSpaceContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#fileNameSpace}.
	 * @param ctx the parse tree
	 */
	void exitFileNameSpace(CFDGParser.FileNameSpaceContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#initialization_v3}.
	 * @param ctx the parse tree
	 */
	void enterInitialization_v3(CFDGParser.Initialization_v3Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#initialization_v3}.
	 * @param ctx the parse tree
	 */
	void exitInitialization_v3(CFDGParser.Initialization_v3Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#initialization_v2}.
	 * @param ctx the parse tree
	 */
	void enterInitialization_v2(CFDGParser.Initialization_v2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#initialization_v2}.
	 * @param ctx the parse tree
	 */
	void exitInitialization_v2(CFDGParser.Initialization_v2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#directive_v2}.
	 * @param ctx the parse tree
	 */
	void enterDirective_v2(CFDGParser.Directive_v2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#directive_v2}.
	 * @param ctx the parse tree
	 */
	void exitDirective_v2(CFDGParser.Directive_v2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#directive_string}.
	 * @param ctx the parse tree
	 */
	void enterDirective_string(CFDGParser.Directive_stringContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#directive_string}.
	 * @param ctx the parse tree
	 */
	void exitDirective_string(CFDGParser.Directive_stringContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#shape}.
	 * @param ctx the parse tree
	 */
	void enterShape(CFDGParser.ShapeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#shape}.
	 * @param ctx the parse tree
	 */
	void exitShape(CFDGParser.ShapeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#shape_singleton_header}.
	 * @param ctx the parse tree
	 */
	void enterShape_singleton_header(CFDGParser.Shape_singleton_headerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#shape_singleton_header}.
	 * @param ctx the parse tree
	 */
	void exitShape_singleton_header(CFDGParser.Shape_singleton_headerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#shape_singleton}.
	 * @param ctx the parse tree
	 */
	void enterShape_singleton(CFDGParser.Shape_singletonContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#shape_singleton}.
	 * @param ctx the parse tree
	 */
	void exitShape_singleton(CFDGParser.Shape_singletonContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#rule_header_v2}.
	 * @param ctx the parse tree
	 */
	void enterRule_header_v2(CFDGParser.Rule_header_v2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#rule_header_v2}.
	 * @param ctx the parse tree
	 */
	void exitRule_header_v2(CFDGParser.Rule_header_v2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#rule_v2}.
	 * @param ctx the parse tree
	 */
	void enterRule_v2(CFDGParser.Rule_v2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#rule_v2}.
	 * @param ctx the parse tree
	 */
	void exitRule_v2(CFDGParser.Rule_v2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#rule_header}.
	 * @param ctx the parse tree
	 */
	void enterRule_header(CFDGParser.Rule_headerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#rule_header}.
	 * @param ctx the parse tree
	 */
	void exitRule_header(CFDGParser.Rule_headerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#path_header}.
	 * @param ctx the parse tree
	 */
	void enterPath_header(CFDGParser.Path_headerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#path_header}.
	 * @param ctx the parse tree
	 */
	void exitPath_header(CFDGParser.Path_headerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#rule_v3}.
	 * @param ctx the parse tree
	 */
	void enterRule_v3(CFDGParser.Rule_v3Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#rule_v3}.
	 * @param ctx the parse tree
	 */
	void exitRule_v3(CFDGParser.Rule_v3Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#path}.
	 * @param ctx the parse tree
	 */
	void enterPath(CFDGParser.PathContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#path}.
	 * @param ctx the parse tree
	 */
	void exitPath(CFDGParser.PathContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#path_header_v2}.
	 * @param ctx the parse tree
	 */
	void enterPath_header_v2(CFDGParser.Path_header_v2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#path_header_v2}.
	 * @param ctx the parse tree
	 */
	void exitPath_header_v2(CFDGParser.Path_header_v2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#path_v2}.
	 * @param ctx the parse tree
	 */
	void enterPath_v2(CFDGParser.Path_v2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#path_v2}.
	 * @param ctx the parse tree
	 */
	void exitPath_v2(CFDGParser.Path_v2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(CFDGParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(CFDGParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#buncha_parameters}.
	 * @param ctx the parse tree
	 */
	void enterBuncha_parameters(CFDGParser.Buncha_parametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#buncha_parameters}.
	 * @param ctx the parse tree
	 */
	void exitBuncha_parameters(CFDGParser.Buncha_parametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#parameter_list}.
	 * @param ctx the parse tree
	 */
	void enterParameter_list(CFDGParser.Parameter_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#parameter_list}.
	 * @param ctx the parse tree
	 */
	void exitParameter_list(CFDGParser.Parameter_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#function_parameter_list}.
	 * @param ctx the parse tree
	 */
	void enterFunction_parameter_list(CFDGParser.Function_parameter_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#function_parameter_list}.
	 * @param ctx the parse tree
	 */
	void exitFunction_parameter_list(CFDGParser.Function_parameter_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#parameter_spec}.
	 * @param ctx the parse tree
	 */
	void enterParameter_spec(CFDGParser.Parameter_specContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#parameter_spec}.
	 * @param ctx the parse tree
	 */
	void exitParameter_spec(CFDGParser.Parameter_specContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#buncha_elements}.
	 * @param ctx the parse tree
	 */
	void enterBuncha_elements(CFDGParser.Buncha_elementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#buncha_elements}.
	 * @param ctx the parse tree
	 */
	void exitBuncha_elements(CFDGParser.Buncha_elementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#buncha_pathOps_v2}.
	 * @param ctx the parse tree
	 */
	void enterBuncha_pathOps_v2(CFDGParser.Buncha_pathOps_v2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#buncha_pathOps_v2}.
	 * @param ctx the parse tree
	 */
	void exitBuncha_pathOps_v2(CFDGParser.Buncha_pathOps_v2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#pathOp_simple_v2}.
	 * @param ctx the parse tree
	 */
	void enterPathOp_simple_v2(CFDGParser.PathOp_simple_v2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#pathOp_simple_v2}.
	 * @param ctx the parse tree
	 */
	void exitPathOp_simple_v2(CFDGParser.PathOp_simple_v2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#element_simple}.
	 * @param ctx the parse tree
	 */
	void enterElement_simple(CFDGParser.Element_simpleContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#element_simple}.
	 * @param ctx the parse tree
	 */
	void exitElement_simple(CFDGParser.Element_simpleContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#one_or_more_elements}.
	 * @param ctx the parse tree
	 */
	void enterOne_or_more_elements(CFDGParser.One_or_more_elementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#one_or_more_elements}.
	 * @param ctx the parse tree
	 */
	void exitOne_or_more_elements(CFDGParser.One_or_more_elementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#one_or_more_pathOp_v2}.
	 * @param ctx the parse tree
	 */
	void enterOne_or_more_pathOp_v2(CFDGParser.One_or_more_pathOp_v2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#one_or_more_pathOp_v2}.
	 * @param ctx the parse tree
	 */
	void exitOne_or_more_pathOp_v2(CFDGParser.One_or_more_pathOp_v2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#caseBody}.
	 * @param ctx the parse tree
	 */
	void enterCaseBody(CFDGParser.CaseBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#caseBody}.
	 * @param ctx the parse tree
	 */
	void exitCaseBody(CFDGParser.CaseBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#caseBody_element}.
	 * @param ctx the parse tree
	 */
	void enterCaseBody_element(CFDGParser.CaseBody_elementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#caseBody_element}.
	 * @param ctx the parse tree
	 */
	void exitCaseBody_element(CFDGParser.CaseBody_elementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#element}.
	 * @param ctx the parse tree
	 */
	void enterElement(CFDGParser.ElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#element}.
	 * @param ctx the parse tree
	 */
	void exitElement(CFDGParser.ElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#element_v2clue}.
	 * @param ctx the parse tree
	 */
	void enterElement_v2clue(CFDGParser.Element_v2clueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#element_v2clue}.
	 * @param ctx the parse tree
	 */
	void exitElement_v2clue(CFDGParser.Element_v2clueContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#pathOp_v2}.
	 * @param ctx the parse tree
	 */
	void enterPathOp_v2(CFDGParser.PathOp_v2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#pathOp_v2}.
	 * @param ctx the parse tree
	 */
	void exitPathOp_v2(CFDGParser.PathOp_v2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#pathOp_v3clues}.
	 * @param ctx the parse tree
	 */
	void enterPathOp_v3clues(CFDGParser.PathOp_v3cluesContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#pathOp_v3clues}.
	 * @param ctx the parse tree
	 */
	void exitPathOp_v3clues(CFDGParser.PathOp_v3cluesContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#element_loop}.
	 * @param ctx the parse tree
	 */
	void enterElement_loop(CFDGParser.Element_loopContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#element_loop}.
	 * @param ctx the parse tree
	 */
	void exitElement_loop(CFDGParser.Element_loopContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#buncha_replacements_v2}.
	 * @param ctx the parse tree
	 */
	void enterBuncha_replacements_v2(CFDGParser.Buncha_replacements_v2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#buncha_replacements_v2}.
	 * @param ctx the parse tree
	 */
	void exitBuncha_replacements_v2(CFDGParser.Buncha_replacements_v2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#one_or_more_replacements_v2}.
	 * @param ctx the parse tree
	 */
	void enterOne_or_more_replacements_v2(CFDGParser.One_or_more_replacements_v2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#one_or_more_replacements_v2}.
	 * @param ctx the parse tree
	 */
	void exitOne_or_more_replacements_v2(CFDGParser.One_or_more_replacements_v2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#replacement_simple_v2}.
	 * @param ctx the parse tree
	 */
	void enterReplacement_simple_v2(CFDGParser.Replacement_simple_v2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#replacement_simple_v2}.
	 * @param ctx the parse tree
	 */
	void exitReplacement_simple_v2(CFDGParser.Replacement_simple_v2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#replacement_v2}.
	 * @param ctx the parse tree
	 */
	void enterReplacement_v2(CFDGParser.Replacement_v2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#replacement_v2}.
	 * @param ctx the parse tree
	 */
	void exitReplacement_v2(CFDGParser.Replacement_v2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#loopHeader_v2}.
	 * @param ctx the parse tree
	 */
	void enterLoopHeader_v2(CFDGParser.LoopHeader_v2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#loopHeader_v2}.
	 * @param ctx the parse tree
	 */
	void exitLoopHeader_v2(CFDGParser.LoopHeader_v2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#loopHeader}.
	 * @param ctx the parse tree
	 */
	void enterLoopHeader(CFDGParser.LoopHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#loopHeader}.
	 * @param ctx the parse tree
	 */
	void exitLoopHeader(CFDGParser.LoopHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#ifHeader}.
	 * @param ctx the parse tree
	 */
	void enterIfHeader(CFDGParser.IfHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#ifHeader}.
	 * @param ctx the parse tree
	 */
	void exitIfHeader(CFDGParser.IfHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#ifElseHeader}.
	 * @param ctx the parse tree
	 */
	void enterIfElseHeader(CFDGParser.IfElseHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#ifElseHeader}.
	 * @param ctx the parse tree
	 */
	void exitIfElseHeader(CFDGParser.IfElseHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#transHeader}.
	 * @param ctx the parse tree
	 */
	void enterTransHeader(CFDGParser.TransHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#transHeader}.
	 * @param ctx the parse tree
	 */
	void exitTransHeader(CFDGParser.TransHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#switchHeader}.
	 * @param ctx the parse tree
	 */
	void enterSwitchHeader(CFDGParser.SwitchHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#switchHeader}.
	 * @param ctx the parse tree
	 */
	void exitSwitchHeader(CFDGParser.SwitchHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#caseHeader}.
	 * @param ctx the parse tree
	 */
	void enterCaseHeader(CFDGParser.CaseHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#caseHeader}.
	 * @param ctx the parse tree
	 */
	void exitCaseHeader(CFDGParser.CaseHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#modification_v2}.
	 * @param ctx the parse tree
	 */
	void enterModification_v2(CFDGParser.Modification_v2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#modification_v2}.
	 * @param ctx the parse tree
	 */
	void exitModification_v2(CFDGParser.Modification_v2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#modification}.
	 * @param ctx the parse tree
	 */
	void enterModification(CFDGParser.ModificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#modification}.
	 * @param ctx the parse tree
	 */
	void exitModification(CFDGParser.ModificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#buncha_adjustments}.
	 * @param ctx the parse tree
	 */
	void enterBuncha_adjustments(CFDGParser.Buncha_adjustmentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#buncha_adjustments}.
	 * @param ctx the parse tree
	 */
	void exitBuncha_adjustments(CFDGParser.Buncha_adjustmentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#adjustment}.
	 * @param ctx the parse tree
	 */
	void enterAdjustment(CFDGParser.AdjustmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#adjustment}.
	 * @param ctx the parse tree
	 */
	void exitAdjustment(CFDGParser.AdjustmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#letHeader}.
	 * @param ctx the parse tree
	 */
	void enterLetHeader(CFDGParser.LetHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#letHeader}.
	 * @param ctx the parse tree
	 */
	void exitLetHeader(CFDGParser.LetHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#letBody}.
	 * @param ctx the parse tree
	 */
	void enterLetBody(CFDGParser.LetBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#letBody}.
	 * @param ctx the parse tree
	 */
	void exitLetBody(CFDGParser.LetBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#letVariables}.
	 * @param ctx the parse tree
	 */
	void enterLetVariables(CFDGParser.LetVariablesContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#letVariables}.
	 * @param ctx the parse tree
	 */
	void exitLetVariables(CFDGParser.LetVariablesContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#letVariable}.
	 * @param ctx the parse tree
	 */
	void enterLetVariable(CFDGParser.LetVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#letVariable}.
	 * @param ctx the parse tree
	 */
	void exitLetVariable(CFDGParser.LetVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#explist}.
	 * @param ctx the parse tree
	 */
	void enterExplist(CFDGParser.ExplistContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#explist}.
	 * @param ctx the parse tree
	 */
	void exitExplist(CFDGParser.ExplistContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#arglist}.
	 * @param ctx the parse tree
	 */
	void enterArglist(CFDGParser.ArglistContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#arglist}.
	 * @param ctx the parse tree
	 */
	void exitArglist(CFDGParser.ArglistContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExp(CFDGParser.ExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExp(CFDGParser.ExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#exp2}.
	 * @param ctx the parse tree
	 */
	void enterExp2(CFDGParser.Exp2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#exp2}.
	 * @param ctx the parse tree
	 */
	void exitExp2(CFDGParser.Exp2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#exp3}.
	 * @param ctx the parse tree
	 */
	void enterExp3(CFDGParser.Exp3Context ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#exp3}.
	 * @param ctx the parse tree
	 */
	void exitExp3(CFDGParser.Exp3Context ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#expfunc}.
	 * @param ctx the parse tree
	 */
	void enterExpfunc(CFDGParser.ExpfuncContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#expfunc}.
	 * @param ctx the parse tree
	 */
	void exitExpfunc(CFDGParser.ExpfuncContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#exp2func}.
	 * @param ctx the parse tree
	 */
	void enterExp2func(CFDGParser.Exp2funcContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#exp2func}.
	 * @param ctx the parse tree
	 */
	void exitExp2func(CFDGParser.Exp2funcContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#shapeName}.
	 * @param ctx the parse tree
	 */
	void enterShapeName(CFDGParser.ShapeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#shapeName}.
	 * @param ctx the parse tree
	 */
	void exitShapeName(CFDGParser.ShapeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#global_definition}.
	 * @param ctx the parse tree
	 */
	void enterGlobal_definition(CFDGParser.Global_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#global_definition}.
	 * @param ctx the parse tree
	 */
	void exitGlobal_definition(CFDGParser.Global_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#function_definition_header}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition_header(CFDGParser.Function_definition_headerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#function_definition_header}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition_header(CFDGParser.Function_definition_headerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#global_definition_header}.
	 * @param ctx the parse tree
	 */
	void enterGlobal_definition_header(CFDGParser.Global_definition_headerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#global_definition_header}.
	 * @param ctx the parse tree
	 */
	void exitGlobal_definition_header(CFDGParser.Global_definition_headerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#definition_header}.
	 * @param ctx the parse tree
	 */
	void enterDefinition_header(CFDGParser.Definition_headerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#definition_header}.
	 * @param ctx the parse tree
	 */
	void exitDefinition_header(CFDGParser.Definition_headerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CFDGParser#definition}.
	 * @param ctx the parse tree
	 */
	void enterDefinition(CFDGParser.DefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CFDGParser#definition}.
	 * @param ctx the parse tree
	 */
	void exitDefinition(CFDGParser.DefinitionContext ctx);
}