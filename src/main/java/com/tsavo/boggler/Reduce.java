package com.tsavo.boggler;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Reduce extends Reducer<Text, Text, Text, Text> {
	private static final String BOARD_TO_SOLVE = "catadogbtestcrand";

	@Override
	protected void reduce(Text key, Iterable<Text> value, Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
		for (Text t : value) {
			BoggleBoard board = new BoggleBoard(BOARD_TO_SOLVE);
			for (String s : board.getWordsContained(Arrays.asList(t.toString()))) {
				context.write(t, new Text(s));
			}
		}
	}

}
